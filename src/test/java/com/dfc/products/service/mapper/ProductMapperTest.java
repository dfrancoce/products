package com.dfc.products.service.mapper;

import com.dfc.products.model.Product;
import com.dfc.products.service.resource.ProductResource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductMapperTest {
    private ProductMapper sut;

    @Before
    public void setUp() {
        sut = new ProductMapper();
    }

    @Test
    public void mapToProductResource() {
        final Product product = getProduct();
        final ProductResource productResource = sut.map(product);

        assertEquals(productResource.getId(), product.getId());
        assertEquals(productResource.getName(), product.getName());
        assertEquals(productResource.getPrice(), product.getPrice());
    }

    @Test
    public void mapToProduct() {
        final ProductResource productResource = getProductResource();
        final Product product = sut.map(productResource);

        assertEquals(product.getId(), productResource.getId());
        assertEquals(product.getName(), productResource.getName());
        assertEquals(product.getPrice(), productResource.getPrice());
    }

    @Test
    public void mapToProductResourceFromAttributes() {
        final Product product = getProduct();
        final ProductResource productResource = sut.map(product, product.getPrice());

        assertEquals(productResource.getId(), product.getId());
        assertEquals(productResource.getName(), product.getName());
        assertEquals(productResource.getPrice(), product.getPrice());
    }

    private Product getProduct() {
        final Product product = new Product();
        product.setId(1L);
        product.setName("Iphone X");
        product.setPrice(1200.0);

        return product;
    }

    private ProductResource getProductResource() {
        final ProductResource productResource = new ProductResource();
        productResource.setId(1L);
        productResource.setName("Iphone X");
        productResource.setPrice(1200.0);

        return productResource;
    }
}