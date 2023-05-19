package vn.group24.shopalbackend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.CreateNewPurchaseOrderRequest;
import vn.group24.shopalbackend.controller.request.CustomerPurchaseOrderCancelRequest;
import vn.group24.shopalbackend.controller.request.EnterpriseUpdateOrderStatusRequest;
import vn.group24.shopalbackend.controller.request.PurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.response.common.OrderStatusDto;
import vn.group24.shopalbackend.controller.response.enterprise.PurchaseOrderDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Membership;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCart;
import vn.group24.shopalbackend.domain.PurchaseOrder;
import vn.group24.shopalbackend.domain.PurchaseOrderDetail;
import vn.group24.shopalbackend.domain.enums.DeliveryStatus;
import vn.group24.shopalbackend.domain.enums.OrderStatus;
import vn.group24.shopalbackend.mapper.admin.AdminPurchaseOrderMapper;
import vn.group24.shopalbackend.mapper.customer.CustomerPurchaseOrderMapper;
import vn.group24.shopalbackend.repository.MembershipRepository;
import vn.group24.shopalbackend.repository.ProductCartRepository;
import vn.group24.shopalbackend.repository.ProductRepository;
import vn.group24.shopalbackend.repository.PurchaseOrderRepository;
import vn.group24.shopalbackend.security.domain.enums.UserRole;
import vn.group24.shopalbackend.service.PurchaseOrderService;
import vn.group24.shopalbackend.util.Validator;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private ProductCartRepository productCartRepository;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerPurchaseOrderMapper customerPurchaseOrderMapper;
    @Autowired
    private AdminPurchaseOrderMapper adminPurchaseOrderMapper;

    @Override
    public void createNewPurchaseOrderForCustomer(Customer customer, List<CreateNewPurchaseOrderRequest> createNewPurchaseOrderRequests) {
        Validator validator = new Validator();
        validator.throwIfFalse(StringUtils.isNotBlank(customer.getFullName()), "Hãy cập nhật thông tin họ tên, số điện thoại, địa chỉ liên lạc để tiến hành đặt hàng");
        validator.throwIfFalse(StringUtils.isNotBlank(customer.getPhoneNumber()), "Hãy cập nhật thông tin họ tên, số điện thoại, địa chỉ liên lạc để tiến hành đặt hàng");
        validator.throwIfFalse(StringUtils.isNotBlank(customer.getAddress()), "Hãy cập nhật thông tin họ tên, số điện thoại, địa chỉ liên lạc để tiến hành đặt hàng");

        // TODO: create query fetch join here
        List<ProductCart> orderProductCarts = productCartRepository.findAllById(createNewPurchaseOrderRequests.stream()
                .map(CreateNewPurchaseOrderRequest::getProductCartId).collect(Collectors.toList()));
        // TODO: fetch join this
        Map<Enterprise, Membership> customerAvailablePointByEnterprise = customer.getMemberships()
                .stream().collect(Collectors.toMap(Membership::getEnterprise, Function.identity()));
        // TODO: fetch join this
        Map<Product, List<ProductCart>> productGroupByOrderProduct = orderProductCarts.stream()
                .collect(Collectors.groupingBy(productCart -> productCart.getProductPoint().getProduct()));

        productGroupByOrderProduct.forEach((product, productCarts) -> {
            Integer totalAmountOrder = productCarts.stream().map(ProductCart::getAmount).reduce(0, Integer::sum);
            Integer remainQuantityInStock = product.getQuantityInStock() - totalAmountOrder;
            validator.throwIfFalse(remainQuantityInStock > 0, "Số lượng sản phẩm đặt [%s] đã vượt quá số hàng tồn, vui lòng thử lại", product.getProductName());
            product.setQuantityInStock(remainQuantityInStock);
            product.setTotalSold(product.getTotalSold() + totalAmountOrder);
        });

        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        orderProductCarts.stream().collect(Collectors.groupingBy(pc -> pc.getProductPoint().getEnterprise()))
                .forEach(((enterprise, orderProductCartsByEnterprise) -> {

                    PurchaseOrder purchaseOrder = new PurchaseOrder();
                    purchaseOrder.setCustomer(customer);
                    purchaseOrder.setEnterprise(enterprise);
                    purchaseOrder.setOrderDate(LocalDateTime.now());
                    //TODO add feature to watch and update order status and delivery status
                    purchaseOrder.setOrderStatus(OrderStatus.OPEN);
                    purchaseOrder.setDeliveryStatus(DeliveryStatus.ORDERED);
                    purchaseOrder.setDeliveryDate(LocalDateTime.now());

                    purchaseOrder.setPurchaseOrderDetails(orderProductCartsByEnterprise.stream().map(productCart -> {
                        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
                        purchaseOrderDetail.setProduct(productCart.getProductPoint().getProduct());
                        purchaseOrderDetail.setProductPoint(productCart.getProductPoint());
                        purchaseOrderDetail.setPointExchange(productCart.getProductPoint().getPointExchange());
                        purchaseOrderDetail.setAmount(productCart.getAmount());
                        purchaseOrderDetail.setPurchaseOrder(purchaseOrder);
                        purchaseOrderDetail.setTotalCash(productCart.getProductPoint().getProduct().getInitialCash().multiply(BigDecimal.valueOf(productCart.getAmount())));
                        purchaseOrderDetail.setTotalPointExchange(purchaseOrderDetail.getPointExchange().multiply(BigDecimal.valueOf(purchaseOrderDetail.getAmount())));
                        return purchaseOrderDetail;
                    }).collect(Collectors.toSet()));

                    Membership membership = customerAvailablePointByEnterprise.get(enterprise);
                    Validate.isTrue(membership != null,
                            "Customer [%s] is not yet a membership of this enterprise [%s]", customer.getContactEmail(), enterprise.getEnterpriseName());

                    BigDecimal availablePoint = membership.getAvailablePoint();
                    BigDecimal orderTotalPointExchange = purchaseOrder.getPurchaseOrderDetails().stream()
                            .map(PurchaseOrderDetail::getTotalPointExchange).reduce(BigDecimal.ZERO, BigDecimal::add);

                    Validate.isTrue(availablePoint.compareTo(orderTotalPointExchange) > 0,
                            "Customer [%s]'s available point for enterprise [%s] is not enough to place this order", customer.getContactEmail(), enterprise.getEnterpriseName());

                    purchaseOrder.setOrderTotalPointExchange(orderTotalPointExchange);
                    purchaseOrder.setOrderTotalCash(purchaseOrder.getPurchaseOrderDetails().stream().map(PurchaseOrderDetail::getTotalCash).reduce(BigDecimal.ZERO, BigDecimal::add));
                    purchaseOrders.add(purchaseOrder);

                    membership.setAvailablePoint(availablePoint.subtract(orderTotalPointExchange));
                }));

        productCartRepository.deleteAll(orderProductCarts);
        purchaseOrderRepository.saveAll(purchaseOrders);
        membershipRepository.saveAll(customerAvailablePointByEnterprise.values());
        productRepository.saveAll(productGroupByOrderProduct.keySet());
    }

    @Override
    public List<PurchaseOrderDto> getPurchaseOrderByCriteria(PurchaseOrderSearchCriteriaRequest criteria) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.getByCriteria(criteria);

        if (StringUtils.isNotBlank(criteria.getProductSku())) {
            purchaseOrders = purchaseOrders.stream().filter(po -> po.getPurchaseOrderDetails().stream().anyMatch(pod -> pod.getProduct().getSku().equals(criteria.getProductSku()))).toList();
        }

        purchaseOrders = purchaseOrders.stream()
                .sorted(Comparator.comparing(PurchaseOrder::getOrderDate).reversed()
                        .thenComparing(PurchaseOrder::getOrderStatus))
                .toList();

        if (UserRole.CUSTOMER == criteria.getUserRole()) {
            return customerPurchaseOrderMapper.mapToPurchaseOrderDtos(purchaseOrders);
        } else {
            return adminPurchaseOrderMapper.mapToPurchaseOrderDtos(purchaseOrders);
        }
    }

    @Override
    public PurchaseOrderDto getPurchaseOrderDetailForEnterprise(Enterprise enterprise, Integer purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElseGet(() -> null);
        Validate.isTrue(purchaseOrder != null, "Can not found Order with id = %s", purchaseOrderId);
        Validate.isTrue(purchaseOrder.getEnterprise().equals(enterprise), "Current order does belong to request enterprise");
        return adminPurchaseOrderMapper.mapToPurchaseOrderDtos(Collections.singletonList(purchaseOrder)).get(0);
    }

    @Override
    public List<OrderStatusDto> getAllOrderStatus() {
        return Arrays.stream(OrderStatus.values()).map(orderStatus -> {
            OrderStatusDto orderStatusDto = new OrderStatusDto();
            orderStatusDto.setOrderStatus(orderStatus);
            orderStatusDto.setOrderStatusDescription(orderStatusDto.getOrderStatusDescription());
            return orderStatusDto;
        }).toList();
    }

    @Override
    public String updatePurchaseOrderStatusForEnterprise(Enterprise enterprise, EnterpriseUpdateOrderStatusRequest request) {
        Validate.isTrue(request.getPurchaseOrderId() != null, "Request PurchaseOrderId can not be null");
        Validate.isTrue(request.getNewOrderStatus() != null, "Request NewOrderStatus can not be null");
        if (OrderStatus.DELIVERED == request.getNewOrderStatus()) {
            // TODO: handle validate delivery date
            request.setDeliveryDate(LocalDateTime.now());
//            Validate.isTrue(request.getDeliveryDate() != null, "Request DeliveryDate can not be null when NewOrderStatus = DELIVERY");
        }
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(request.getPurchaseOrderId()).orElseGet(() -> null);
        Validate.isTrue(purchaseOrder != null, "Can not found Order with id = %s", request.getPurchaseOrderId());
        Validate.isTrue(purchaseOrder.getEnterprise().equals(enterprise), "Current order does belong to request enterprise");
        Validate.isTrue(purchaseOrder.getOrderStatus() != request.getNewOrderStatus(), "New Status can not be the same with Old Status");

        purchaseOrder.setOrderStatus(request.getNewOrderStatus());
        if (OrderStatus.DELIVERED == request.getNewOrderStatus()) {
            purchaseOrder.setDeliveryDate(request.getDeliveryDate());
            purchaseOrder.setDeliveryStatus(DeliveryStatus.DELIVERED);
        }
        purchaseOrderRepository.save(purchaseOrder);
        return "Update PurchaseOrderStatus ok";
    }

    @Override
    public String cancelOrderForCustomer(Customer customer, CustomerPurchaseOrderCancelRequest request) {
        // TODO: fetch join this
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(request.getPurchaseOrderId()).orElseGet(() -> null);
        Validate.isTrue(purchaseOrder != null, "Can not found PurchaseOrder with id = %s", request.getPurchaseOrderId());
        Validate.isTrue(OrderStatus.CANCELLED == purchaseOrder.getOrderStatus(), "Can not cancel PurchaseOrder with status = %s", purchaseOrder.getOrderStatus());

        // update order status
        purchaseOrder.setOrderStatus(OrderStatus.CANCELLED);
        purchaseOrder.setCancelDate(LocalDateTime.now());
        // TODO: config request cancel reason
        purchaseOrder.setCancelReason(request.getCancelReason());
        purchaseOrderRepository.save(purchaseOrder);

        // restore customer point
        Membership membership = membershipRepository.getByCustomerIdAndEnterpriseId(customer.getId(), purchaseOrder.getEnterprise().getId());
        membership.setAvailablePoint(membership.getAvailablePoint().add(purchaseOrder.getOrderTotalPointExchange()));
        membershipRepository.save(membership);

        // restore product quantity in stock and amount sold
        purchaseOrder.getPurchaseOrderDetails().forEach(purchaseOrderDetail -> {
            Product product = purchaseOrderDetail.getProduct();
            product.setQuantityInStock(product.getQuantityInStock() + purchaseOrderDetail.getAmount());
            product.setTotalSold(product.getTotalSold() - purchaseOrderDetail.getAmount());
            productRepository.save(product);
        });

        return "Cancel PurchaseOrder succefully";
    }
}
