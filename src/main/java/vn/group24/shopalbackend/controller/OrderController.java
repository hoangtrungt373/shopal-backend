package vn.group24.shopalbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.group24.shopalbackend.controller.request.CreateNewPurchaseOrderRequest;
import vn.group24.shopalbackend.controller.request.CustomerPurchaseOrderCancelRequest;
import vn.group24.shopalbackend.controller.request.PurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.UpdateOrderStatusRequest;
import vn.group24.shopalbackend.controller.response.common.OrderStatusDto;
import vn.group24.shopalbackend.controller.response.enterprise.PurchaseOrderDto;
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

    @PostMapping("/get-by-crireria")
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<PurchaseOrderDto>> getPurchaseOrderByCriteria(@RequestBody PurchaseOrderSearchCriteriaRequest request) {
        return ResponseEntity.ok().body(purchaseOrderService.getPurchaseOrderByCriteria(request));
    }


    @PostMapping("/update-order-status")
//    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> updatePurchaseOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok().body(purchaseOrderService.updatePurchaseOrderStatus(request));
    }

    @PostMapping("/current-customer/cancel-order")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> cancelOrderForCurrentCustomer(@RequestBody CustomerPurchaseOrderCancelRequest request) {
        return ResponseEntity.ok().body(purchaseOrderService.cancelOrderForCustomer(userUtils.getAuthenticateCustomer(), request));
    }

    @GetMapping("/order-status/get-all")
    public ResponseEntity<List<OrderStatusDto>> getAllOrderStatus() {
        List<OrderStatusDto> orderStatusDtos = purchaseOrderService.getAllOrderStatus();
        return ResponseEntity.ok().body(orderStatusDtos);
    }
}
