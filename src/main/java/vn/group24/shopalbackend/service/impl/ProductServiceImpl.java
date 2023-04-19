package vn.group24.shopalbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.controller.response.specific.ProductDetailDto;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.mapper.ProductMapper;
import vn.group24.shopalbackend.repository.ProductRepository;
import vn.group24.shopalbackend.service.ProductService;

/**
 * @author ttg
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDetailDto getProductDetail(Integer productId) {
        Product product = productRepository.getProductDetail(productId);
        if (product == null) {
            throw new IllegalArgumentException(String.format("Can not found product with id = %s", productId));
        }
        return productMapper.mapToProductDetailDto(product);
    }
}
