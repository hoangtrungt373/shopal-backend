package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CartDto addOrRemoveProductCartForCustomer(Integer customerId, Integer productId, Integer amount, Integer productPointId) {
        Validate.isTrue(amount >= 0, "Product amount can not negative");

        Customer customer = customerRepository.findById(customerId).orElseGet(() -> null);
        Product product = productRepository.findById(productId).orElseGet(() -> null);
        ProductPoint productPoint = productPointRepository.findById(productPointId).orElseGet(() -> null);

        Validate.isTrue(customer != null, String.format("Can not found Customer with id = %s", customerId));
        Validate.isTrue(product != null && BooleanUtils.isTrue(product.getActive()), String.format("Can not found active Product with id = %s", productId));
        Validate.isTrue(productPoint != null && BooleanUtils.isTrue(productPoint.getActive()), String.format("Can not found active ProductPoint with id = %s", productPointId));

        ProductCart productCart = productCartRepository.getByProductIdAndProductPointIdAndCustomerId(productId, productPointId, customer.getId());
        if (amount == 0) {
            productCartRepository.delete(productCart);
        } else {
            productCart.setAmount(amount);
            productCartRepository.save(productCart);
        }
        return getAllProductCartForCustomer(customerId);
    }

    @Override
    public CartDto getAllProductCartForCustomer(Integer customerId) {
        List<ProductCart> productCarts = productCartRepository.getAllProductCartByCustomerId(customerId);
        return cartMapper.mapToCartDto(productCarts);
    }

}
