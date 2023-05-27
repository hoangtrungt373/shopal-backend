package vn.group24.shopalbackend.mapper.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.common.ProductDto;
import vn.group24.shopalbackend.controller.response.enterprise.PurchaseOrderDetailDto;
import vn.group24.shopalbackend.controller.response.enterprise.PurchaseOrderDto;
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
public class CustomerPurchaseOrderMapper {

    public List<PurchaseOrderDto> mapToPurchaseOrderDtos(List<PurchaseOrder> purchaseOrders) {
        List<PurchaseOrderDto> purchaseOrderDtos = new ArrayList<>();
        purchaseOrders.forEach(purchaseOrder -> {
            PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
            purchaseOrderDto.setId(purchaseOrder.getId());
            purchaseOrderDto.setOrderDate(purchaseOrder.getOrderDate());
            purchaseOrderDto.setOrderStatus(purchaseOrder.getOrderStatus());
            purchaseOrderDto.setOrderStatusDescription(purchaseOrder.getOrderStatus().getTextForCurrentLan());
            purchaseOrderDto.setDeliveryDate(purchaseOrder.getDeliveryDate());
            purchaseOrderDto.setOrderTotalPointExchange(purchaseOrder.getOrderTotalPointExchange());
            purchaseOrderDto.setEnterprise(mapToEnterpriseDto(purchaseOrder.getEnterprise()));
            purchaseOrderDto.setPurchaseProducts(purchaseOrder.getPurchaseOrderDetails().stream()
                    .map(this::mapToPurchaseOrderDetailDto).toList());

            purchaseOrderDtos.add(purchaseOrderDto);
        });
        return purchaseOrderDtos;
    }

    private EnterpriseDto mapToEnterpriseDto(Enterprise enterprise) {
        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(enterprise.getId());
        enterpriseDto.setEnterpriseName(enterprise.getEnterpriseName());
        enterpriseDto.setLogoUrl(enterprise.getLogoUrl());
        return enterpriseDto;
    }

    private PurchaseOrderDetailDto mapToPurchaseOrderDetailDto(PurchaseOrderDetail purchaseOrderDetail) {
        PurchaseOrderDetailDto purchaseOrderDetailDto = new PurchaseOrderDetailDto();
        purchaseOrderDetailDto.setId(purchaseOrderDetail.getId());
        purchaseOrderDetailDto.setPointExchange(purchaseOrderDetail.getPointExchange());
        purchaseOrderDetailDto.setAmount(purchaseOrderDetail.getAmount());
        purchaseOrderDetailDto.setTotalPointExchange(purchaseOrderDetail.getTotalPointExchange());
        purchaseOrderDetailDto.setProductId(purchaseOrderDetail.getProduct().getId());
        purchaseOrderDetailDto.setTotalCash(purchaseOrderDetail.getTotalCash());
        purchaseOrderDetailDto.setProduct(mapToProductDto(purchaseOrderDetail.getProduct()));
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
