package com.dfc.products.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dfc.products.model.Product;
import com.dfc.products.repository.ProductRepository;
import com.dfc.products.service.mapper.ProductMapper;
import com.dfc.products.service.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResource create(final ProductResource productResource) {
        final Product dbProduct = productRepository.save(productMapper.map(productResource));
        return productMapper.map(dbProduct);
    }

    public ProductResource update(final ProductResource updatedProductResource, final Long id) {
        return productRepository.findById(id).map(product -> {
            if (updatedProductResource.getPrice() != null) product.setPrice(updatedProductResource.getPrice());
            if (updatedProductResource.getName() != null) product.setName(updatedProductResource.getName());

            final Product dbProduct = productRepository.save(product);
            return productMapper.map(dbProduct);
        }).orElseGet(() -> {
            final Product product = productMapper.map(updatedProductResource);
            product.setId(id);

            final Product dbProduct = productRepository.save(product);
            return productMapper.map(dbProduct);
        });
    }

    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

    public ProductResource get(final Long id) {
        final Optional<Product> dbProduct = productRepository.findById(id);

        if (dbProduct.isPresent()) {
            final Product product = dbProduct.get();
            return productMapper.map(product);
        } else {
            return null;
        }
    }

    public List<ProductResource> get() {
        final List<Product> products = productRepository.findAll();
        return products.stream().map(product -> productMapper.map(product)).collect(Collectors.toList());
    }
}
