package vn.group24.shopalbackend.service;

import java.util.List;

import vn.group24.shopalbackend.controller.request.CreateNewPurchaseOrderRequest;
import vn.group24.shopalbackend.controller.request.CustomerPurchaseOrderCancelRequest;
import vn.group24.shopalbackend.controller.request.PurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.UpdateOrderStatusRequest;
import vn.group24.shopalbackend.controller.response.common.OrderStatusDto;
import vn.group24.shopalbackend.controller.response.enterprise.PurchaseOrderDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;

/**
 * @author ttg
 */
public interface PurchaseOrderService {

    void createNewPurchaseOrderForCustomer(Customer customer, List<CreateNewPurchaseOrderRequest> createNewPurchaseOrderRequests);

    List<PurchaseOrderDto> getPurchaseOrderByCriteria(PurchaseOrderSearchCriteriaRequest criteria);

    PurchaseOrderDto getPurchaseOrderDetailForEnterprise(Enterprise enterprise, Integer purchaseOrderId);

    List<OrderStatusDto> getAllOrderStatus();

    String updatePurchaseOrderStatus(UpdateOrderStatusRequest request);

    String cancelOrderForCustomer(Customer customer, CustomerPurchaseOrderCancelRequest request);
}
