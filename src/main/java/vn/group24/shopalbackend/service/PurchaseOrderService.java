package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.request.CreateNewPurchaseOrderRequest;
import vn.group24.shopalbackend.controller.request.EnterprisePurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.EnterpriseUpdateOrderStatusRequest;
import vn.group24.shopalbackend.controller.response.common.OrderStatusDto;
import vn.group24.shopalbackend.controller.response.customer.CustomerPurchaseOrderDto;
import vn.group24.shopalbackend.controller.response.enterprise.EnterprisePurchaseOrderDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;

/**
 * @author ttg
 */
public interface PurchaseOrderService {

    void createNewPurchaseOrderForCustomer(Customer customer, List<CreateNewPurchaseOrderRequest> createNewPurchaseOrderRequests);

    List<CustomerPurchaseOrderDto> getAllPurchaseOrderForCustomer(Customer customer);

    List<EnterprisePurchaseOrderDto> getPurchaseOrderForEnterpriseByCriteria(Enterprise enterprise, EnterprisePurchaseOrderSearchCriteriaRequest criteria);

    EnterprisePurchaseOrderDto getPurchaseOrderDetailForEnterprise(Enterprise enterprise, Integer purchaseOrderId);

    List<OrderStatusDto> getAllOrderStatus();

    String updatePurchaseOrderStatusForEnterprise(Enterprise enterprise, EnterpriseUpdateOrderStatusRequest request);
}
