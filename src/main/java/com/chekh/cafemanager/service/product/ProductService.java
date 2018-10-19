package com.chekh.cafemanager.service.product;

import com.chekh.cafemanager.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProductsWith(List<String> ids) {
        return productRepository.findAllById(ids);
    }

    public Product getProductWith(String productId) {
        return productRepository.getOne(productId);
    }
}
