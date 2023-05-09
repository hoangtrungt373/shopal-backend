package vn.group24.shopalbackend.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.CustomerDto;
import vn.group24.shopalbackend.controller.response.common.EnterpriseDto;
import vn.group24.shopalbackend.controller.response.customer.CustomerPurchaseOrderDto;
import vn.group24.shopalbackend.controller.response.customer.PurchaseOrderDetailDto;
import vn.group24.shopalbackend.controller.response.enterprise.EnterprisePurchaseOrderDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.ProductImage;
import vn.group24.shopalbackend.domain.PurchaseOrder;
import vn.group24.shopalbackend.domain.PurchaseOrderDetail;

/**
 *
 * @author ttg
 */
@Component
public class PurchaseOrderMapper {

    public List<CustomerPurchaseOrderDto> mapToCustomerPurchaseOrderDtos(List<PurchaseOrder> purchaseOrders) {
        List<CustomerPurchaseOrderDto> customerPurchaseOrderDtos = new ArrayList<>();
        purchaseOrders.forEach(purchaseOrder -> {
            CustomerPurchaseOrderDto customerPurchaseOrderDto = new CustomerPurchaseOrderDto();
            customerPurchaseOrderDto.setId(purchaseOrder.getId());
            customerPurchaseOrderDto.setOrderDate(purchaseOrder.getOrderDate());
            customerPurchaseOrderDto.setOrderStatus(purchaseOrder.getOrderStatus());
            customerPurchaseOrderDto.setOrderStatusDescription(purchaseOrder.getOrderStatus().getTextForCurrentLan());
            customerPurchaseOrderDto.setDeliveryDate(purchaseOrder.getDeliveryDate());
            customerPurchaseOrderDto.setOrderTotalPointExchange(purchaseOrder.getOrderTotalPointExchange());
            customerPurchaseOrderDto.setEnterprise(mapToEnterpriseDto(purchaseOrder.getEnterprise()));
            customerPurchaseOrderDto.setPurchaseProducts(purchaseOrder.getPurchaseOrderDetails().stream()
                    .map(this::mapToPurchaseOrderDetailDto).toList());

            customerPurchaseOrderDtos.add(customerPurchaseOrderDto);
        });
        return customerPurchaseOrderDtos;
    }

    public List<EnterprisePurchaseOrderDto> mapToEnterprisePurchaseOrderDtos(List<PurchaseOrder> purchaseOrders) {
        List<EnterprisePurchaseOrderDto> enterprisePurchaseOrderDtos = new ArrayList<>();
        purchaseOrders.forEach(purchaseOrder -> {
            EnterprisePurchaseOrderDto enterprisePurchaseOrderDto = new EnterprisePurchaseOrderDto();
            enterprisePurchaseOrderDto.setId(purchaseOrder.getId());
            enterprisePurchaseOrderDto.setOrderDate(purchaseOrder.getOrderDate());
            enterprisePurchaseOrderDto.setOrderStatus(purchaseOrder.getOrderStatus());
            enterprisePurchaseOrderDto.setOrderStatusDescription(purchaseOrder.getOrderStatus().getTextForCurrentLan());
            enterprisePurchaseOrderDto.setDeliveryDate(purchaseOrder.getDeliveryDate());
            enterprisePurchaseOrderDto.setOrderTotalPointExchange(purchaseOrder.getOrderTotalPointExchange());
            enterprisePurchaseOrderDto.setCustomerFullName(purchaseOrder.getCustomer().getFullName());
            enterprisePurchaseOrderDto.setCustomerContactEmail(purchaseOrder.getCustomer().getContactEmail());
            enterprisePurchaseOrderDto.setPurchaseProducts(purchaseOrder.getPurchaseOrderDetails().stream()
                    .map(this::mapToPurchaseOrderDetailDto).toList());
            enterprisePurchaseOrderDto.setOrderTotalItems(purchaseOrder.getPurchaseOrderDetails()
                    .stream().map(PurchaseOrderDetail::getAmount).reduce(0, Integer::sum));
            enterprisePurchaseOrderDto.setCustomer(mapToCustomerDto(purchaseOrder.getCustomer()));
            enterprisePurchaseOrderDto.setOrderTotalCash(purchaseOrder.getOrderTotalCash());
            enterprisePurchaseOrderDtos.add(enterprisePurchaseOrderDto);
        });
        return enterprisePurchaseOrderDtos;
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
        purchaseOrderDetailDto.setPurchaseOrderDetailId(purchaseOrderDetail.getId());
        purchaseOrderDetailDto.setPointExchange(purchaseOrderDetail.getPointExchange());
        purchaseOrderDetailDto.setAmount(purchaseOrderDetail.getAmount());
        purchaseOrderDetailDto.setTotalPointExchange(purchaseOrderDetail.getTotalPointExchange());
        purchaseOrderDetailDto.setInitialCash(purchaseOrderDetail.getProduct().getInitialCash());
        purchaseOrderDetailDto.setProductId(purchaseOrderDetail.getProduct().getId());
        purchaseOrderDetailDto.setProductName(purchaseOrderDetail.getProduct().getProductName());
        purchaseOrderDetailDto.setSku(purchaseOrderDetail.getProduct().getSku());
        purchaseOrderDetailDto.setTotalCash(purchaseOrderDetail.getTotalCash());
        purchaseOrderDetailDto.setMainImgUrl(purchaseOrderDetail.getProduct().getProductImages().stream()
                .filter(ProductImage::getIsMainImg)
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElseGet(() -> purchaseOrderDetail.getProduct().getProductImages().stream()
                        .findAny()
                        .map(ProductImage::getImageUrl)
                        .orElseGet(() -> null)));
        return purchaseOrderDetailDto;
    }
}
