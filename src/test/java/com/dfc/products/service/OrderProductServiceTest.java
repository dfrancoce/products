package com.dfc.products.service;

import com.dfc.products.model.Order;
import com.dfc.products.model.OrderProduct;
import com.dfc.products.model.Product;
import com.dfc.products.repository.OrderProductRepository;
import com.dfc.products.repository.OrderRepository;
import com.dfc.products.repository.ProductRepository;
import com.dfc.products.service.mapper.ProductMapper;
import com.dfc.products.service.resource.ProductResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderProductServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private OrderProduct orderProduct;
    @Mock
    private OrderProduct orderProduct2;
    @Mock
    private Order order;
    @Mock
    private ProductResource productResourceById;
    @Mock
    private ProductResource productResourceByName;
    @Mock
    private Product productById;
    @Mock
    private Product productByName;
    @Mock
    private ProductResource productResource;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private OrderProductService sut;

    @Before
    public void setUp() {
        when(productById.getId()).thenReturn(1L);
        when(productByName.getId()).thenReturn(null);
        when(productByName.getName()).thenReturn("Apple Macbook pro");

        when(order.getId()).thenReturn(1L);
        when(orderProduct.getPrice()).thenReturn(1000.0);
        when(orderProduct2.getPrice()).thenReturn(1000.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productById));
        when(productRepository.findProductByName("Apple Macbook pro")).thenReturn(productByName);
        when(orderProductRepository.save(any())).thenReturn(orderProduct);
        when(orderProductRepository.findById(any())).thenReturn(Optional.of(orderProduct));
        when(orderProductRepository.findAllByOrder(order)).thenReturn(Arrays.asList(orderProduct, orderProduct2));
        doNothing().when(orderProductRepository).delete(any());

        when(productMapper.map(any(), anyDouble())).thenReturn(productResource);
        when(productMapper.map(productResourceById)).thenReturn(productById);
        when(productMapper.map(productResourceByName)).thenReturn(productByName);
    }

    @Test
    public void addProductsToOrder() {
        // when
        sut.addProductsToOrder(Arrays.asList(productResourceById, productResourceByName), 1L);

        // then
        verify(orderProductRepository, times(2)).save(any());
    }

    @Test
    public void deleteProductsFromOrder() {
        // when
        sut.deleteProductsFromOrder(Arrays.asList(productResourceById, productResourceByName), 1L);

        // then
        verify(orderProductRepository, times(2)).delete(any());
    }

    @Test
    public void calculate() {
        // when
        final Double sum = sut.calculate(1L);

        // then
        assertEquals(2000.0, sum, 0);
    }

    @Test
    public void getProductsOfAnOrder() {
        // when
        final List<ProductResource> productResources = sut.getProductsOfAnOrder(1L);

        // then
        assertEquals(2, productResources.size());
    }
}