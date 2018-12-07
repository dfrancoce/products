package com.dfc.products.service;

import com.dfc.products.model.Product;
import com.dfc.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product create(final Product product) {
        return productRepository.save(product);
    }

    public Product update(final Product updatedProduct, final Long id) {
        return productRepository.findById(id).map(product -> {
            if (updatedProduct.getPrice() != null) product.setPrice(updatedProduct.getPrice());
            if (updatedProduct.getName() != null) product.setName(updatedProduct.getName());

            return productRepository.save(product);
        }).orElseGet(() -> {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        });
    }

    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

    public Product get(final Long id) {
        final Optional<Product> dbProduct = productRepository.findById(id);
        return dbProduct.orElse(null);
    }

    public List<Product> get() {
        return productRepository.findAll();
    }
}
