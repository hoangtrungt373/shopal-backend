package vn.group24.shopalbackend.mapper.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.enterprise.PurchaseOrderDetailDto;
import vn.group24.shopalbackend.controller.response.enterprise.PurchaseOrderDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductGallery;
import vn.group24.shopalbackend.domain.PurchaseOrder;
import vn.group24.shopalbackend.domain.PurchaseOrderDetail;
import vn.group24.shopalbackend.domain.enums.ProductReviewType;

/**
 *
 * @author ttg
 */
@Component
public class AdminPurchaseOrderMapper {

    public List<PurchaseOrderDto> mapToPurchaseOrderDtos(List<PurchaseOrder> purchaseOrders) {
        List<PurchaseOrderDto> purchaseOrderAllInfoDtos = new ArrayList<>();
        purchaseOrders.forEach(purchaseOrder -> {
            PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
            purchaseOrderDto.setId(purchaseOrder.getId());
            purchaseOrderDto.setOrderDate(purchaseOrder.getOrderDate());
            purchaseOrderDto.setOrderStatus(purchaseOrder.getOrderStatus());
            purchaseOrderDto.setOrderStatusDescription(purchaseOrder.getOrderStatus().getTextForCurrentLan());
            purchaseOrderDto.setDeliveryDate(purchaseOrder.getDeliveryDate());
            purchaseOrderDto.setOrderTotalPointExchange(purchaseOrder.getOrderTotalPointExchange());
            purchaseOrderDto.setPurchaseProducts(purchaseOrder.getPurchaseOrderDetails().stream()
                    .map(this::mapToPurchaseOrderDetailDto).toList());
            purchaseOrderDto.setOrderTotalItems(purchaseOrder.getPurchaseOrderDetails()
                    .stream().map(PurchaseOrderDetail::getAmount).reduce(0, Integer::sum));
            purchaseOrderDto.setCustomer(mapToCustomerDto(purchaseOrder.getCustomer()));
            purchaseOrderDto.setEnterprise(mapToEnterpriseDto(purchaseOrder.getEnterprise()));
            purchaseOrderDto.setOrderTotalCash(purchaseOrder.getOrderTotalCash());
            purchaseOrderAllInfoDtos.add(purchaseOrderDto);
        });
        return purchaseOrderAllInfoDtos;
    }

    private EnterpriseDto mapToEnterpriseDto(Enterprise enterprise) {
        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(enterprise.getId());
        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        return enterpriseDto;
    }

    private CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFullName(customer.getFullName());
        customerDto.setContactEmail(customer.getContactEmail());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setAddress(customer.getAddress());
        customerDto.setAvatarUrl(customer.getAvatarUrl());
        customerDto.setBirthDate(customer.getBirthDate());
        customerDto.setGender(customer.getGender());
        customerDto.setGenderDescription(customer.getGender().getTextForCurrentLan());
        return customerDto;
    }

    private PurchaseOrderDetailDto mapToPurchaseOrderDetailDto(PurchaseOrderDetail purchaseOrderDetail) {
        PurchaseOrderDetailDto purchaseOrderDetailDto = new PurchaseOrderDetailDto();
        purchaseOrderDetailDto.setId(purchaseOrderDetail.getId());
        purchaseOrderDetailDto.setProductDto(mapToProductDto(purchaseOrderDetail.getProduct()));
        purchaseOrderDetailDto.setPointExchange(purchaseOrderDetail.getPointExchange());
        purchaseOrderDetailDto.setAmount(purchaseOrderDetail.getAmount());
        purchaseOrderDetailDto.setTotalPointExchange(purchaseOrderDetail.getTotalPointExchange());
        purchaseOrderDetailDto.setTotalCash(purchaseOrderDetail.getTotalCash());
        purchaseOrderDetailDto.setIsReview(purchaseOrderDetail.getProductReviews().stream()
                .anyMatch(pr -> ProductReviewType.REVIEW == pr.getReviewType()));
        return purchaseOrderDetailDto;
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setInitialCash(product.getInitialCash());
        productDto.setProductName(product.getProductName());
        productDto.setSku(product.getSku());
        productDto.setMainImgUrl(product.getProductGalleries().stream()
                .filter(ProductGallery::getIsMainFile)
                .findFirst()
                .map(ProductGallery::getFileUrl)
                .orElseGet(() -> product.getProductGalleries().stream()
                        .findAny()
                        .map(ProductGallery::getFileUrl)
                        .orElseGet(() -> null)));
        return productDto;
    }

}
