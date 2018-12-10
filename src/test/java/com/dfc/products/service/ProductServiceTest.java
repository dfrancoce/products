package com.dfc.products.service;

import com.dfc.products.model.Product;
import com.dfc.products.repository.ProductRepository;
import com.dfc.products.service.mapper.ProductMapper;
import com.dfc.products.service.resource.ProductResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductResource productResource;
    @Mock
    private Product product;
    @InjectMocks
    private ProductService sut;

    @Before
    public void setUp() {
        when(productMapper.map(product)).thenReturn(productResource);
        when(productMapper.map(productResource)).thenReturn(product);

        when(productRepository.save(product)).thenReturn(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        doNothing().when(productRepository).deleteById(1L);

        when(productResource.getName()).thenReturn("Apple Iphone X");
        when(productResource.getPrice()).thenReturn(1200.00);
    }

    @Test
    public void create() {
        // when
        final ProductResource retProductResource = sut.create(productResource);

        // then
        assertEquals(productResource, retProductResource);
    }

    @Test
    public void update() {
        // when
        final ProductResource retProductResource = sut.update(productResource, 1L);

        // then
        assertEquals(productResource, retProductResource);
    }

    @Test
    public void updateWhenProductNotFound() {
        // when
        final ProductResource retProductResource = sut.update(productResource, 1L);

        // then
        assertEquals(productResource, retProductResource);
    }

    @Test
    public void delete() {
        // when
        sut.delete(1L);

        // then
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getWhenExists() {
        // when
        final ProductResource retProductResource = sut.get(1L);

        // then
        assertEquals(productResource, retProductResource);
    }

    @Test
    public void getWhenDoesntExist() {
        // when
        final ProductResource retProductResource = sut.get(2L);

        // then
        assertNull(retProductResource);
    }

    @Test
    public void getAll() {
        // when
        final List<ProductResource> productResources = sut.get();

        // then
        assertEquals(1, productResources.size());
    }
}