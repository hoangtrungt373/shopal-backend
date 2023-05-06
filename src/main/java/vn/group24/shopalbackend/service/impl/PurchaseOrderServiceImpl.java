package vn.group24.shopalbackend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.CreateNewPurchaseOrderRequest;
import vn.group24.shopalbackend.controller.request.EnterprisePurchaseOrderSearchCriteriaRequest;
import vn.group24.shopalbackend.controller.request.EnterpriseUpdateOrderStatusRequest;
import vn.group24.shopalbackend.controller.response.common.OrderStatusDto;
import vn.group24.shopalbackend.controller.response.customer.CustomerPurchaseOrderDto;
import vn.group24.shopalbackend.controller.response.enterprise.EnterprisePurchaseOrderDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Membership;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCart;
import vn.group24.shopalbackend.domain.PurchaseOrder;
import vn.group24.shopalbackend.domain.PurchaseOrderDetail;
import vn.group24.shopalbackend.domain.enums.DeliveryStatus;
import vn.group24.shopalbackend.domain.enums.OrderStatus;
import vn.group24.shopalbackend.domain.multilingual.OrderStatusLan;
import vn.group24.shopalbackend.domain.multilingual.SysLanguage;
import vn.group24.shopalbackend.mapper.LanguageMapper;
import vn.group24.shopalbackend.mapper.PurchaseOrderMapper;
import vn.group24.shopalbackend.repository.MembershipRepository;
import vn.group24.shopalbackend.repository.OrderStatusLanRepository;
import vn.group24.shopalbackend.repository.ProductCartRepository;
import vn.group24.shopalbackend.repository.ProductRepository;
import vn.group24.shopalbackend.repository.PurchaseOrderRepository;
import vn.group24.shopalbackend.service.PurchaseOrderService;

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
    private OrderStatusLanRepository orderStatusLanRepository;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private LanguageMapper languageMapper;

    @Override
    public void createNewPurchaseOrderForCustomer(Customer customer, List<CreateNewPurchaseOrderRequest> createNewPurchaseOrderRequests) {
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
            int totalAmountOrder = productCarts.stream().map(ProductCart::getAmount).reduce(0, Integer::sum);
            int remainQuantityInStock = product.getQuantityInStock() - totalAmountOrder;
            Validate.isTrue(remainQuantityInStock > 0, "Amount of order product [%s] can not larger product's quantity in stock [%s]", totalAmountOrder, product.getQuantityInStock());
            product.setQuantityInStock(remainQuantityInStock);
            product.setAmountSold(product.getAmountSold() + totalAmountOrder);
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
                    purchaseOrders.add(purchaseOrder);

                    membership.setAvailablePoint(availablePoint.subtract(orderTotalPointExchange));
                }));

        productCartRepository.deleteAll(orderProductCarts);
        purchaseOrderRepository.saveAll(purchaseOrders);
        membershipRepository.saveAll(customerAvailablePointByEnterprise.values());
        productRepository.saveAll(productGroupByOrderProduct.keySet());
    }

    @Override
    public List<CustomerPurchaseOrderDto> getAllPurchaseOrderForCustomer(Customer customer) {
        // TODO: fetch join this
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.getByCustomerId(customer.getId());
        return purchaseOrderMapper.mapToCustomerPurchaseOrderDtos(purchaseOrders);
    }

    @Override
    public List<EnterprisePurchaseOrderDto> getPurchaseOrderForEnterpriseByCriteria(Enterprise enterprise, EnterprisePurchaseOrderSearchCriteriaRequest criteria) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.getByEnterpriseId(enterprise.getId()).stream()
                .filter(po -> getPurchaseOrderPredicate(criteria).test(po))
                .sorted(Comparator.comparing(PurchaseOrder::getOrderDate).reversed()).toList();
        return purchaseOrderMapper.mapToEnterprisePurchaseOrderDtos(purchaseOrders);
    }

    private Predicate<PurchaseOrder> getPurchaseOrderPredicate(EnterprisePurchaseOrderSearchCriteriaRequest criteria) {
        if (criteria.getStartDate() == null) {
            criteria.setStartDate(LocalDate.MIN);
        }
        if (criteria.getEndDate() == null) {
            criteria.setEndDate(LocalDate.MAX);
        }
        Predicate<PurchaseOrder> predicate = po -> !po.getOrderDate().isBefore(LocalDateTime.of(criteria.getStartDate(), LocalTime.MIN)) &&
                !po.getOrderDate().isAfter(LocalDateTime.of(criteria.getEndDate(), LocalTime.MAX));

        if (criteria.getOrderStatus() != null) {
            predicate = predicate.and(po -> criteria.getOrderStatus() == po.getOrderStatus());
        }

        return predicate;
    }

    @Override
    public EnterprisePurchaseOrderDto getPurchaseOrderDetailForEnterprise(Enterprise enterprise, Integer purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElseGet(() -> null);
        Validate.isTrue(purchaseOrder != null, "Can not found Order with id = %s", purchaseOrderId);
        Validate.isTrue(purchaseOrder.getEnterprise().equals(enterprise), "Current order does belong to request enterprise");
        return purchaseOrderMapper.mapToEnterprisePurchaseOrderDtos(Collections.singletonList(purchaseOrder)).get(0);
    }

    @Override
    public List<OrderStatusDto> getAllOrderStatus() {
        // TODO: optimize these method get enum lan description
        List<OrderStatusLan> orderStatusLans = orderStatusLanRepository.getByLanguage(SysLanguage.getCurrentLanguage());
        return languageMapper.mapToOrderStatusDto(orderStatusLans);
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
}
