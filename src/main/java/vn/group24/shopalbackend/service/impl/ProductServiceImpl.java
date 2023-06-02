package vn.group24.shopalbackend.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.group24.shopalbackend.controller.request.AdminCreateOrUpdateProductRequest;
import vn.group24.shopalbackend.controller.request.AnnouncementSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.CustomerProductReviewRequest;
import vn.group24.shopalbackend.controller.request.ProductSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.admin.CreateOrUpdateProductResponse;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.customer.ProductDetailDto;
import vn.group24.shopalbackend.domain.Announcement;
import vn.group24.shopalbackend.domain.CooperationContract;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCatalog;
import vn.group24.shopalbackend.domain.ProductGallery;
import vn.group24.shopalbackend.domain.ProductPoint;
import vn.group24.shopalbackend.domain.ProductReview;
import vn.group24.shopalbackend.domain.PurchaseOrderDetail;
import vn.group24.shopalbackend.domain.dto.AbstractAnn;
import vn.group24.shopalbackend.domain.dto.AnnouncementInput;
import vn.group24.shopalbackend.domain.dto.EnterpriseProductSellingRequestAnn;
import vn.group24.shopalbackend.domain.enums.AnnouncementInterface;
import vn.group24.shopalbackend.domain.enums.AnnouncementStatus;
import vn.group24.shopalbackend.domain.enums.AnnouncementType;
import vn.group24.shopalbackend.domain.enums.ContractStatus;
import vn.group24.shopalbackend.domain.enums.EnterpriseProductSellingRequestStatus;
import vn.group24.shopalbackend.domain.enums.ProductReviewType;
import vn.group24.shopalbackend.domain.enums.ProductStatus;
import vn.group24.shopalbackend.mapper.AbstractMapper;
import vn.group24.shopalbackend.mapper.ProductDetailMapper;
import vn.group24.shopalbackend.mapper.ProductMapper;
import vn.group24.shopalbackend.repository.AnnouncementRepository;
import vn.group24.shopalbackend.repository.CatalogRepository;
import vn.group24.shopalbackend.repository.CooperationContractRepository;
import vn.group24.shopalbackend.repository.EnterpriseRepository;
import vn.group24.shopalbackend.repository.ProductCartRepository;
import vn.group24.shopalbackend.repository.ProductPointRepository;
import vn.group24.shopalbackend.repository.ProductRepository;
import vn.group24.shopalbackend.repository.ProductReviewRepository;
import vn.group24.shopalbackend.repository.PurchaseOrderDetailRepository;
import vn.group24.shopalbackend.security.domain.enums.UserRole;
import vn.group24.shopalbackend.service.AnnouncementService;
import vn.group24.shopalbackend.service.ProductService;
import vn.group24.shopalbackend.util.BigDecimalUtils;
import vn.group24.shopalbackend.util.Constants;
import vn.group24.shopalbackend.util.FileUtils;
import vn.group24.shopalbackend.util.Validator;

/**
 * @author ttg
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private ProductPointRepository productPointRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;
    @Autowired
    private CooperationContractRepository cooperationContractRepository;
    @Autowired
    private ProductCartRepository productCartRepository;
    @Autowired
    private ObjectMapper jsonObjectMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private AbstractMapper abstractMapper;

    @Override
    public ProductDetailDto getProductDetail(Integer productId) {
        Product product = productRepository.getDetailById(productId);
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
                List<Integer> enterpriseIds = p.getProductPoints().stream().filter(pp -> BooleanUtils.isTrue(pp.getActive())).map(ProductPoint::getEnterprise).map(Enterprise::getId).toList();
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
        Validator validator = new Validator();
        Validate.isTrue(existsProductPoint == null, "Request enterprise already selling this product");
        Validate.isTrue(existsProduct != null, "Can not found product with id = %s", productId);

        validator.throwIfFalse(existsCooperationContract != null, "Not exists active Contract in current date for request enterprise");
        validator.throwIfFalse(enterprise.getLogoUrl() != null, "Please update enterprise LogoUrl to request selling product");

        Announcement existsAnnouncement = announcementService.getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest.builder()
                .productId(productId)
                .enterpriseId(enterprise.getId())
                .status(AnnouncementStatus.VALID)
                .anInterface(AnnouncementInterface.REQ_PRODUCT_SELLING)
                .type(AnnouncementType.RECEIVE)
                .build()).stream().findFirst().orElseGet(() -> null);
        validator.throwIfFalse(existsAnnouncement == null, "Already exists an request selling for this product");

        EnterpriseProductSellingRequestAnn announcement = new EnterpriseProductSellingRequestAnn();
        announcement.setEnterpriseProductSellingRequestStatus(EnterpriseProductSellingRequestStatus.RECEIVED);
        announcement.setProductId(productId);
        announcement.setEnterpriseId(enterprise.getId());
        announcement.setCashPerPoint(existsCooperationContract.getCashPerPoint());
        announcement.setCooperationContractId(existsCooperationContract.getId());

        try {
            announcementService.createAnnouncement(AnnouncementInput.builder()
                    .anInterface(AnnouncementInterface.REQ_PRODUCT_SELLING)
                    .type(AnnouncementType.RECEIVE)
                    .status(AnnouncementStatus.VALID)
                    .product(existsProduct)
                    .enterprise(enterprise)
                    .message(jsonObjectMapper.writeValueAsString(announcement))
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "Received product selling request for enterprise " + enterprise.getEnterpriseName() + " successfully";
    }

    @Override
    public List<EnterpriseProductSellingRequestAnn> getAllEnterpriseProductSellingRequestAnn() {
        List<Announcement> announcements = new ArrayList<>();

        announcements.addAll(announcementService.getAnnouncementByCriteria(AnnouncementSearchCriteriaRequest.builder()
                .anInterface(AnnouncementInterface.REQ_PRODUCT_SELLING)
                .type(AnnouncementType.RECEIVE)
                .build()));

        Map<Announcement, EnterpriseProductSellingRequestAnn> announcementMap = new HashMap<>();
        announcements.forEach(announcement -> {
            try {
                EnterpriseProductSellingRequestAnn messageDto =
                        jsonObjectMapper.readValue(announcement.getMessage(), EnterpriseProductSellingRequestAnn.class);
                announcementMap.put(announcement, messageDto);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(
                        "Unable to deserialize the message for EnterpriseProductSellingRequestAnn");
            }
        });

        Map<Integer, Enterprise> enterpriseMap = enterpriseRepository.findAllById(announcementMap.values().stream().map(AbstractAnn::getEnterpriseId)
                        .filter(Objects::nonNull).toList())
                .stream().collect(Collectors.toMap(Enterprise::getId, Function.identity()));

        Map<Integer, Product> productMap = productRepository.findAllById(announcementMap.values().stream().map(AbstractAnn::getProductId)
                        .filter(Objects::nonNull).toList())
                .stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        announcementMap.forEach((announcement, messageDto) -> {
            messageDto.setAnnouncementId(announcement.getId());
            messageDto.setDate(announcement.getDate());
            messageDto.setEnterpriseProductSellingRequestStatusDescription(abstractMapper.getTextForCurrentLan(messageDto.getEnterpriseProductSellingRequestStatus()));
            messageDto.setEnterprise(Optional.ofNullable(enterpriseMap.get(messageDto.getEnterpriseId()))
                    .map(enterprise -> {
                        EnterpriseDto enterpriseDto = new EnterpriseDto();
                        enterpriseDto.setId(enterprise.getId());
                        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
                        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
                        return enterpriseDto;
                    }).orElseGet(() -> null));
            messageDto.setProduct(Optional.ofNullable(productMap.get(messageDto.getProductId()))
                    .map(product -> {
                        return productMapper.mapToProductDtos(Collections.singletonList(product)).get(0);
                    }).orElseGet(() -> null));
        });

        return announcementMap.values().stream().sorted(Comparator.comparing(EnterpriseProductSellingRequestAnn::getDate).reversed()
                .thenComparing(EnterpriseProductSellingRequestAnn::getEnterpriseProductSellingRequestStatus)).collect(Collectors.toList());
    }

    @Override
    public String handleAcceptRequestSellingProduct(EnterpriseProductSellingRequestAnn enterpriseProductSellingRequestAnn) {
        Product product = productRepository.findById(enterpriseProductSellingRequestAnn.getProductId()).orElseGet(() -> null);
        Validate.isTrue(product != null, "Can not found product with id = %s", enterpriseProductSellingRequestAnn.getProductId());

        Enterprise enterprise = enterpriseRepository.findById(enterpriseProductSellingRequestAnn.getEnterpriseId()).orElseGet(() -> null);
        Validate.isTrue(enterprise != null, "Can not found enterprise with id = %s", enterpriseProductSellingRequestAnn.getEnterpriseId());

        CooperationContract cooperationContract = cooperationContractRepository.findById(enterpriseProductSellingRequestAnn.getCooperationContractId()).orElseGet(() -> null);
        Validate.isTrue(cooperationContract != null, "Can not found Cooperation Contract with id = %s", enterpriseProductSellingRequestAnn.getCooperationContractId());
        Validator validator = new Validator();
        validator.throwIfFalse(ContractStatus.ACTIVE == cooperationContract.getContractStatus(), "Request contract must be active");

        ProductPoint newProductPoint = new ProductPoint();
        newProductPoint.setProduct(product);
        newProductPoint.setEnterprise(enterprise);
        newProductPoint.setActive(Boolean.TRUE);
        newProductPoint.setPointExchange(BigDecimalUtils.divide(product.getInitialCash(), cooperationContract.getCashPerPoint(), 0, 2));
        productPointRepository.save(newProductPoint);

        enterpriseProductSellingRequestAnn.setEnterpriseProductSellingRequestStatus(EnterpriseProductSellingRequestStatus.ACCEPT);

        Announcement announcement = announcementRepository.findById(enterpriseProductSellingRequestAnn.getAnnouncementId()).orElseGet(() -> null);
        Validate.isTrue(announcement != null, "Can not found Announcement with id = %s", enterpriseProductSellingRequestAnn.getAnnouncementId());

        announcement.setStatus(AnnouncementStatus.TRANSMITTED);
        try {
            announcement.setMessage(jsonObjectMapper.writeValueAsString(enterpriseProductSellingRequestAnn));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        announcementRepository.save(announcement);

        return "Accept product selling for enterprise " + enterprise.getEnterpriseName() + " successfully";
    }

    @Override
    public String handleRequestCancellingProductForEnterprise(Enterprise enterprise, Integer productId) {
        ProductPoint existsProductPoint = productPointRepository.getByEnterpriseIdAndProductIdAndActiveIsTrue(enterprise.getId(), productId);
        Validate.isTrue(existsProductPoint != null, "Request enterprise do not register to sell this product at this moment");

        existsProductPoint.setActive(Boolean.FALSE);
        productPointRepository.save(existsProductPoint);
        return "Cancel selling product succefully";
    }

    @Override
    public CreateOrUpdateProductResponse createOrUpdateProduct(AdminCreateOrUpdateProductRequest request, MultipartFile[] images) throws IOException {
        validateProductInfoCaseCreate(request);
        if (request.getProductId() == null) { //case create
            Product newProduct = new Product();
            newProduct.setSku(StringUtils.isNotBlank(request.getSku()) ? request.getSku() : nextProductSku());
            newProduct.setProductName(request.getProductName());
            newProduct.setQuantityInStock(request.getQuantityInStock());
            newProduct.setProductStatus(request.getProductStatus());
            newProduct.setRating(BigDecimal.ZERO);
            newProduct.setInputDate(LocalDate.now());
            newProduct.setExpirationDate(request.getExpirationDate() == null ? LocalDate.of(2023, 12, 31) : request.getExpirationDate());
            newProduct.setInitialCash(request.getInitialCash());
            newProduct.setTotalSold(0);
            newProduct.setTotalReview(0);

            // save gallery
            for (int i = 0; i < images.length; i++) {
                ProductGallery newProductGallery = new ProductGallery();
                newProductGallery.setProduct(newProduct);
                newProductGallery.setFileUrl(FileUtils.saveFileWithRandomName(images[i], Constants.PRODUCT_IMAGE_DIRECTORY));
                newProductGallery.setIsMainFile(i == 0 ? Boolean.TRUE : Boolean.FALSE);
                newProduct.addProductGallery(newProductGallery);
            }
            newProduct.setGalleryUrls(newProduct.getProductGalleries().stream().map(ProductGallery::getFileUrl).collect(Collectors.joining(",")));

            // save content
            String productDescriptionUrl = newProduct.getSku().concat(Constants.TXT_FILE_EXTENSION);
            FileUtils.writeFile(StringUtils.stripToEmpty(request.getContent()), productDescriptionUrl, Constants.PRODUCT_CONTENT_DIRECTORY);
            newProduct.setProductDescriptionUrl(productDescriptionUrl);

            newProduct.setProductType(request.getProductType());
            ProductCatalog newProductCatalog = new ProductCatalog();
            newProductCatalog.setCatalog(catalogRepository.findById(request.getCatalogId()).orElseGet(() -> null));
            newProduct.addProductCatalog(newProductCatalog);

            productRepository.save(newProduct);

            return CreateOrUpdateProductResponse.builder()
                    .message("Create new Product successfully")
                    .productId(newProduct.getId())
                    .sku(newProduct.getSku())
                    .build();

        } else { // case update
            //TODO handle case edit
            Product oldGenerationProduct = productRepository.getDetailById(request.getProductId());
            Validate.isTrue(oldGenerationProduct != null, "Can not found Product by Id = %s", request.getProductId());

            Product newGenerationProduct = oldGenerationProduct.copy();
            newGenerationProduct.setSku(StringUtils.isNotBlank(request.getSku()) ? request.getSku() : nextProductSku());
            newGenerationProduct.setProductName(request.getProductName());
            newGenerationProduct.setQuantityInStock(request.getQuantityInStock());
            newGenerationProduct.setProductDescriptionUrl(request.getProductDescriptionUrl());
            newGenerationProduct.setProductStatus(request.getProductStatus());
            newGenerationProduct.setExpirationDate(request.getExpirationDate() == null ? LocalDate.of(2023, 12, 31) : request.getExpirationDate());
            newGenerationProduct.setInitialCash(request.getInitialCash());
            newGenerationProduct.setProductType(request.getProductType());

            // update gallery
            newGenerationProduct.getProductGalleries().forEach(productGallery -> {
                FileUtils.deleteFile(productGallery.getFileUrl(), Constants.PRODUCT_IMAGE_DIRECTORY);
            });
            newGenerationProduct.getProductGalleries().clear();
            for (int i = 0; i < images.length; i++) {
                ProductGallery newProductGallery = new ProductGallery();
                newProductGallery.setProduct(newGenerationProduct);
                newProductGallery.setFileUrl(FileUtils.saveFileWithRandomName(images[i], Constants.PRODUCT_IMAGE_DIRECTORY));
                newProductGallery.setIsMainFile(i == 0 ? Boolean.TRUE : Boolean.FALSE);
                newGenerationProduct.addProductGallery(newProductGallery);
            }
            newGenerationProduct.setGalleryUrls(newGenerationProduct.getProductGalleries().stream().map(ProductGallery::getFileUrl).collect(Collectors.joining(",")));

            // update content
            String productDescriptionUrl = newGenerationProduct.getSku().concat(Constants.TXT_FILE_EXTENSION);
            FileUtils.writeFile(request.getContent(), productDescriptionUrl, Constants.PRODUCT_CONTENT_DIRECTORY);
            newGenerationProduct.setProductDescriptionUrl(productDescriptionUrl);

            // update catalog
            if (!request.getCatalogId().equals(oldGenerationProduct.getProductCatalogs().stream().map(pc -> pc.getCatalog().getId()).findFirst().orElseGet(() -> null))) {
                ProductCatalog newProductCatalog = new ProductCatalog();
                newProductCatalog.setCatalog(catalogRepository.findById(request.getCatalogId()).orElseGet(() -> null));
                newGenerationProduct.getProductCatalogs().clear();
                newGenerationProduct.addProductCatalog(newProductCatalog);
            }

            // update cart and point
            if (!request.getInitialCash().equals(oldGenerationProduct.getInitialCash())) {
                newGenerationProduct.getProductPoints().forEach(productPoint -> {
                    CooperationContract existsCooperationContract = cooperationContractRepository
                            .getByEnterpriseIdAndContractStatus(productPoint.getEnterprise().getId(), ContractStatus.ACTIVE);
                    productPoint.setPointExchange(BigDecimalUtils.divide(newGenerationProduct.getInitialCash(), existsCooperationContract.getCashPerPoint(), 0, 2));
                });
            }

            productRepository.save(newGenerationProduct);

            oldGenerationProduct.setProductStatus(ProductStatus.INACTIVE);
            productRepository.save(oldGenerationProduct);

            return CreateOrUpdateProductResponse.builder()
                    .message("Update Product successfully")
                    .productId(newGenerationProduct.getId())
                    .sku(newGenerationProduct.getSku())
                    .build();
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

    private void validateProductInfoCaseCreate(AdminCreateOrUpdateProductRequest request) {
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
