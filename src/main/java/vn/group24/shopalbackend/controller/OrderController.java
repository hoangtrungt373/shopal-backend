package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.CreateNewPurchaseOrderRequest;
import vn.group24.shopalbackend.controller.request.EnterprisePurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.EnterpriseUpdateOrderStatusRequest;
import vn.group24.shopalbackend.controller.response.common.OrderStatusDto;
import vn.group24.shopalbackend.controller.response.customer.CustomerPurchaseOrderDto;
import vn.group24.shopalbackend.controller.response.enterprise.EnterprisePurchaseOrderDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.service.PurchaseOrderService;

/**
 *
 * @author ttg
 */
@RestController
@RequestMapping("/api/order")
public class OrderController extends AbstractController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/current-customer/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> createNewPurchaseOrderForCurrentCustomer(@RequestBody List<CreateNewPurchaseOrderRequest> request) {
        Customer currentCustomer = userUtils.getAuthenticateCustomer();
        purchaseOrderService.createNewPurchaseOrderForCustomer(userUtils.getAuthenticateCustomer(), request);
        return ResponseEntity.ok().body(String.format("Create new order for customer %s successfully", currentCustomer.getContactEmail()));
    }

    @GetMapping("/current-customer/order-history/get-all")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CustomerPurchaseOrderDto>> getAllPurchaseOrderForCurrentCustomer() {
        List<CustomerPurchaseOrderDto> customerPurchaseOrderDtos = purchaseOrderService.getAllPurchaseOrderForCustomer(userUtils.getAuthenticateCustomer());
        return ResponseEntity.ok().body(customerPurchaseOrderDtos);
    }

    @PostMapping("/current-enterprise/customer-order/get-by-criteria")
//    @PreAuthorize("hasRole('ENTERPRISE_MANAGER')")
    public ResponseEntity<List<EnterprisePurchaseOrderDto>> getPurchaseOrderForCurrentEnterpriseByCriteria(@RequestBody EnterprisePurchaseOrderSearchCriteriaRequest criteria) {
        List<EnterprisePurchaseOrderDto> enterprisePurchaseOrderDtos = purchaseOrderService.getPurchaseOrderForEnterpriseByCriteria(userUtils.getAuthenticateEnterprise(), criteria);
        return ResponseEntity.ok().body(enterprisePurchaseOrderDtos);
    }
    
    @GetMapping("/current-enterprise/customer-order/get-detail/{purchaseOrderId}")
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<EnterprisePurchaseOrderDto> getPurchaseOrderDetailForCurrentEnterprise(@PathVariable Integer purchaseOrderId) {
        EnterprisePurchaseOrderDto enterprisePurchaseOrderDto = purchaseOrderService.getPurchaseOrderDetailForEnterprise(userUtils.getAuthenticateEnterprise(), purchaseOrderId);
        return ResponseEntity.ok().body(enterprisePurchaseOrderDto);
    }

    @PostMapping("/current-enterprise/customer-order/update-order-status")
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> updatePurchaseOrderStatusForCurrentEnterprise(@RequestBody EnterpriseUpdateOrderStatusRequest request) {
        return ResponseEntity.ok().body(purchaseOrderService.updatePurchaseOrderStatusForEnterprise(userUtils.getAuthenticateEnterprise(), request));
    }

    @GetMapping("/order-status/get-all")
    public ResponseEntity<List<OrderStatusDto>> getAllOrderStatus() {
        List<OrderStatusDto> orderStatusDtos = purchaseOrderService.getAllOrderStatus();
        return ResponseEntity.ok().body(orderStatusDtos);
    }
}
