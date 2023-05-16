package vn.group24.shopalbackend.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.group24.shopalbackend.controller.request.AdminCreateOrUpdateProductRequest;
import vn.group24.shopalbackend.controller.request.CustomerProductReviewRequest;
import vn.group24.shopalbackend.controller.request.ProductSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.customer.ProductDetailDto;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCatalog;
import vn.group24.shopalbackend.domain.ProductImage;
import vn.group24.shopalbackend.domain.ProductPoint;
import vn.group24.shopalbackend.domain.ProductReview;
import vn.group24.shopalbackend.domain.PurchaseOrderDetail;
import vn.group24.shopalbackend.domain.enums.ContractStatus;
import vn.group24.shopalbackend.domain.enums.ProductReviewType;
import vn.group24.shopalbackend.mapper.ProductDetailMapper;
import vn.group24.shopalbackend.mapper.ProductMapper;
import vn.group24.shopalbackend.repository.CatalogRepository;
import vn.group24.shopalbackend.repository.ProductPointRepository;
import vn.group24.shopalbackend.repository.ProductRepository;
import vn.group24.shopalbackend.repository.ProductReviewRepository;
import vn.group24.shopalbackend.repository.PurchaseOrderDetailRepository;
import vn.group24.shopalbackend.security.domain.enums.UserRole;
import vn.group24.shopalbackend.service.ProductService;
import vn.group24.shopalbackend.util.BigDecimalUtils;
import vn.group24.shopalbackend.util.Constants;
import vn.group24.shopalbackend.util.FileUtils;

/**
 * @author ttg
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPointRepository productPointRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDetailDto getProductDetailForCustomer(Integer productId) {
        Product product = productRepository.getProductDetailById(productId);
        if (product == null) {
            throw new IllegalArgumentException(String.format("Can not found product with id = %s", productId));
        }
        return productDetailMapper.mapToProductDetailDto(product);
    }

    @Override
    public ProductDetailDto getProductDetailForAdmin(Integer productId) {
        Product product = productRepository.findById(productId).orElseGet(() -> null);
        if (product == null) {
            throw new IllegalArgumentException(String.format("Can not found product with id = %s", productId));
        }
        return productDetailMapper.mapToProductDetailDto(product);
    }

    @Override
    public List<ProductDto> getProductByCriteria(ProductSearchCriteriaRequest criteria) {
        if (criteria.getLimit() == null) {
            criteria.setLimit(Constants.DEFAULT_SEARCH_LIMIT);
        }

        if (criteria.getOffset() == null) {
            criteria.setOffset(Constants.DEFAULT_SEARCH_OFFSET);
        }

        if (criteria.getUserRole() == null) {
            criteria.setUserRole(UserRole.CUSTOMER);
        }

        List<Product> products = productRepository.getByCriteria(criteria);

        if (CollectionUtils.isNotEmpty(criteria.getEnterpriseIdList())) {
            products = products.stream().filter(p -> {
                List<Integer> enterpriseIds = p.getProductPoints().stream().map(ProductPoint::getEnterprise).map(Enterprise::getId).toList();
                return criteria.getEnterpriseIdList().stream().anyMatch(enterpriseIds::contains);
            }).toList();
        }

        return productMapper.mapToProductDtos(products);
    }

    @Override
    public Integer countProductByCriteria(ProductSearchCriteriaRequest criteria) {
        criteria.setLimit(Constants.DEFAULT_SEARCH_LIMIT);
        criteria.setOffset(Constants.DEFAULT_SEARCH_OFFSET);
        if (criteria.getUserRole() == null) {
            criteria.setUserRole(UserRole.CUSTOMER);
        }

        List<Product> products = productRepository.getByCriteria(criteria);

        // TODO: get amount only
        if (CollectionUtils.isNotEmpty(criteria.getEnterpriseIdList())) {
            products = products.stream().filter(p -> {
                List<Integer> enterpriseIds = p.getProductPoints().stream().map(ProductPoint::getEnterprise).map(Enterprise::getId).toList();
                return criteria.getEnterpriseIdList().stream().anyMatch(enterpriseIds::contains);
            }).toList();
        }

        return products.size();
    }

    @Override
    public String handleRequestSellingProductForEnterprise(Enterprise enterprise, Integer productId) {
        ProductPoint existsProductPoint = productPointRepository.getByEnterpriseIdAndProductIdAndActiveIsTrue(enterprise.getId(), productId);
        Product existsProduct = productRepository.findById(productId).orElseGet(() -> null);
        // TODO: check contract in period last day of month
        CooperationContract existsCooperationContract = enterprise.getCooperationContracts()
                .stream().filter(cc -> ContractStatus.ACTIVE == cc.getContractStatus())
                .findFirst().orElseGet(() -> null);
        Validate.isTrue(existsProductPoint == null, "Request enterprise already selling this product");
        Validate.isTrue(existsProduct != null, "Can not found product with id = %s", productId);
        Validate.isTrue(existsCooperationContract != null, "Not exists active Contract in current date for request enterprise");

        ProductPoint newProductPoint = new ProductPoint();
        newProductPoint.setProduct(existsProduct);
        newProductPoint.setEnterprise(enterprise);
        newProductPoint.setActive(Boolean.TRUE);
        newProductPoint.setUpdateDescription(Constants.INITIAL_CREATE);
        newProductPoint.setPointExchange(BigDecimalUtils.divide(existsProduct.getInitialCash(), existsCooperationContract.getCashPerPoint(), 0, 2));
        productPointRepository.save(newProductPoint);
        return "Registration to sell product succefully";
    }

    @Override
    public String handleRequestCancellingProductForEnterprise(Enterprise enterprise, Integer productId) {
        ProductPoint existsProductPoint = productPointRepository.getByEnterpriseIdAndProductIdAndActiveIsTrue(enterprise.getId(), productId);
        Validate.isTrue(existsProductPoint != null, "Request enterprise do not register to sell this product at this moment");

        existsProductPoint.setActive(Boolean.FALSE);
        productPointRepository.save(existsProductPoint);
        return "Registration to sell product succefully";
    }

    @Override
    public String createOrUpdateProduct(AdminCreateOrUpdateProductRequest request, MultipartFile[] images) throws IOException {
        if (request.getProductId() == null) { //case create
            validateProductInfo(request);
            Product newProduct = new Product();
            newProduct.setSku(StringUtils.isNotBlank(request.getSku()) ? request.getSku() : nextProductSku());
            newProduct.setProductName(request.getProductName());
            newProduct.setQuantityInStock(request.getQuantityInStock());
            newProduct.setProductDescriptionUrl(request.getProductDescriptionUrl());
            newProduct.setProductStatus(request.getProductStatus());
            newProduct.setRating(BigDecimal.ZERO);
            newProduct.setInputDate(LocalDate.now());
            newProduct.setExpirationDate(request.getExpirationDate() == null ? LocalDate.of(2023, 12, 31) : request.getExpirationDate());
            newProduct.setInitialCash(request.getInitialCash());
            newProduct.setTotalSold(0);
            newProduct.setTotalReview(0);
            for (int i = 0; i < images.length; i++) {
                ProductImage newProductImage = new ProductImage();
                newProductImage.setProduct(newProduct);
                newProductImage.setImageUrl(FileUtils.saveFileWithRandomName(images[i], Constants.PRODUCT_IMAGE_DIRECTORY));
                newProductImage.setIsMainImg(i == 0 ? Boolean.TRUE : Boolean.FALSE);
                newProduct.addProductImage(newProductImage);
            }
            newProduct.setProductType(request.getProductType());
            ProductCatalog newProductCatalog = new ProductCatalog();
            newProductCatalog.setCatalog(catalogRepository.findById(request.getCatalogId()).orElseGet(() -> null));
            newProduct.addProductCatalog(newProductCatalog);
            productRepository.save(newProduct);
            return "Create new Product successfully";
        } else { // create update
            return null;
        }
    }

    @Override
    public String addPurchaseOrderProductReviewByCustomer(Customer customer, CustomerProductReviewRequest request, MultipartFile[] images) throws IOException {
        Validate.isTrue(request.getPurchaseOrderDetailId() != null, "Request purchaseOrderDetail can not be null");
        Validate.isTrue(request.getRating() != null && request.getRating().compareTo(BigDecimal.ZERO) > 0, "Request rating must large than 0");

        PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailRepository.findById(request.getPurchaseOrderDetailId()).orElseGet(() -> null);
        Validate.isTrue(purchaseOrderDetail != null, "Can not found PurchaseOrderDetail with id = %s", request.getPurchaseOrderDetailId());
        Product product = purchaseOrderDetail.getProduct();
        List<ProductReview> existsProductReviews = productReviewRepository.getByProductIdAndCustomerIdAndReviewType(product.getId(), customer.getId(), ProductReviewType.REVIEW);
        Validate.isTrue(CollectionUtils.isEmpty(existsProductReviews), "Customer already review this product");

        ProductReview productReview = new ProductReview();
        productReview.setProduct(product);
        productReview.setCustomer(customer);
        productReview.setPurchaseOrderDetail(purchaseOrderDetail);
        productReview.setReviewType(ProductReviewType.REVIEW);
        productReview.setReviewDate(LocalDateTime.now());
        productReview.setAmountLike(0);
        productReview.setContent(request.getContent());
        productReview.setRating(request.getRating());
        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                imageUrls.add(FileUtils.saveFileWithRandomName(image, Constants.PRODUCT_REVIEW_DIRECTORY));
            }
        }
        productReview.setImageUrls(imageUrls.toString());
        product.addProductReview(productReview);

        product.setTotalReview(product.getTotalReview() + 1);
        product.setRating(product.getProductReviews().stream().map(ProductReview::getRating).reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(product.getTotalReview()), RoundingMode.CEILING));
        productRepository.save(product);
        return "Add product review successfully";
    }

    private void validateProductInfo(AdminCreateOrUpdateProductRequest request) {
        Validate.isTrue(request.getProductName() != null, "Product name can not be null");
        Validate.isTrue(request.getQuantityInStock() != null && request.getQuantityInStock() > 0, "Quantity in stock must be > 0");
        Validate.isTrue(request.getInitialCash() != null &&
                        request.getInitialCash().compareTo(BigDecimal.valueOf(500)) > 0 &&
                        String.valueOf(request.getInitialCash()).endsWith("0"),
                "Initial cash must be >= 1000 vnd");
        Validate.isTrue(request.getCatalogId() != null && catalogRepository.existsById(request.getCatalogId()), "Catalog with id = [%s] must exists");
        Validate.isTrue(request.getProductStatus() != null, "Product status can not be null");
        Validate.isTrue(request.getProductType() != null, "Product type can not be null");
        if (request.getExpirationDate() != null) {
            Validate.isTrue(!request.getExpirationDate().isBefore(LocalDate.now()), "Expiration date must be > current date");
        }
        if (StringUtils.isNotBlank(request.getSku())) {
            Product existsProductBySku = productRepository.getBySku(request.getSku());
            Validate.isTrue(existsProductBySku == null, "Duplicate product sku");
        }
    }

    @Override
    public String nextProductSku() {
        return String.format("%1$8s", productRepository.getNextSku()).replace(' ', '0');
    }
}
