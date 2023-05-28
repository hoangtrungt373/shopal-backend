package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.request.UpdateProductCartRequest;
import vn.group24.shopalbackend.controller.response.common.CartDto;
import vn.group24.shopalbackend.domain.Customer;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.ProductCart;
import vn.group24.shopalbackend.domain.ProductPoint;
import vn.group24.shopalbackend.mapper.CartMapper;
import vn.group24.shopalbackend.repository.CustomerRepository;
import vn.group24.shopalbackend.repository.ProductCartRepository;
import vn.group24.shopalbackend.repository.ProductPointRepository;
import vn.group24.shopalbackend.repository.ProductRepository;
import vn.group24.shopalbackend.service.CartService;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductCartRepository productCartRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPointRepository productPointRepository;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public CartDto updateProductCartsForCustomer(Customer customer, List<UpdateProductCartRequest> updateProductCartRequests) {
        updateProductCartRequests.forEach(updateProductCartRequest -> {
            Integer amount = updateProductCartRequest.getAmount();
            Integer productPointId = updateProductCartRequest.getProductPointId();
            Integer productCartId = updateProductCartRequest.getProductCartId();

            Validate.isTrue(amount >= 0, "Product amount can not negative");

            ProductPoint productPoint = productPointRepository.findById(productPointId).orElseGet(() -> null);
            Validate.isTrue(productPoint != null, "Can not found ProductPoint with id = %s", productPointId);

            Product product = productPoint.getProduct();
            Validate.isTrue(product.getQuantityInStock() > amount, "Amount order can not large than amount of product in stock, amount order = %s", amount);

            if (productCartId != null) { // user edit in CartPage
                ProductCart productCart = productCartRepository.findById(productCartId)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Can not found ProductCart with id = %s", productCartId)));
                if (amount == 0) {
                    productCartRepository.delete(productCart);
                } else {
                    productCart.setAmount(amount);
                    productCart.setProductPoint(productPoint);
                    productCartRepository.save(productCart);
                }
            } else { // user add new in ProductDetail page
                ProductCart productCart = productCartRepository.getByCustomerIdAndProductPointId(customer.getId(), productPointId);
                if (productCart != null) {
                    productCart.setAmount(productCart.getAmount() + amount);
                } else {
                    productCart = new ProductCart();
                    productCart.setCustomer(customer);
                    productCart.setProductPoint(productPoint);
                    productCart.setAmount(amount);
                }
                productCartRepository.save(productCart);
            }
        });


        return getAllProductCartForCustomer(customer);
    }

    @Override
    public CartDto getAllProductCartForCustomer(Customer customer) {
        List<ProductCart> productCarts = productCartRepository.getAllProductCartByCustomerId(customer.getId());
        return cartMapper.mapToCartDto(productCarts);
    }

}
