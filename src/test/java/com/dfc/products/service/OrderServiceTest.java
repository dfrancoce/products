package com.dfc.products.service;

import com.dfc.products.model.Order;
import com.dfc.products.repository.OrderRepository;
import com.dfc.products.service.mapper.OrderMapper;
import com.dfc.products.service.resource.OrderResource;
import com.dfc.products.service.resource.ProductResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderProductService orderProductService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderResource orderResource;
    @Mock
    private ProductResource productResource;
    @Mock
    private Date fromDate;
    @Mock
    private Date toDate;
    @Mock
    private Order order;
    @InjectMocks
    private OrderService sut;

    @Before
    public void setUp() {
        when(orderMapper.map(order)).thenReturn(orderResource);
        when(orderMapper.map(orderResource)).thenReturn(order);

        when(orderRepository.save(order)).thenReturn(order);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        when(orderRepository.findByOrderDateBetween(fromDate, toDate)).thenReturn(Collections.singletonList(order));
        doNothing().when(orderRepository).deleteById(1L);

        when(orderProductService.getProductsOfAnOrder(1L))
                .thenReturn(Collections.singletonList(productResource));

        when(orderResource.getEmail()).thenReturn("test@gmail.com");
        when(orderResource.getOrderDate()).thenReturn(fromDate);
    }

    @Test
    public void create() {
        // when
        final OrderResource retOrderResource = sut.create(orderResource);

        // then
        assertEquals(orderResource, retOrderResource);
    }

    @Test
    public void update() {
        // when
        final OrderResource retOrderResource = sut.update(orderResource, 1L);

        // then
        assertEquals(orderResource, retOrderResource);
    }

    @Test
    public void updateWhenOrderNotFound() {
        // when
        final OrderResource retOrderResource = sut.update(orderResource, 2L);

        // then
        assertEquals(orderResource, retOrderResource);
    }

    @Test
    public void delete() {
        // when
        sut.delete(1L);

        // then
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getWhenExists() {
        // when
        final OrderResource retOrderResource = sut.get(1L);

        // then
        assertEquals(orderResource, retOrderResource);
    }

    @Test
    public void getWhenDoesntExist() {
        // when
        final OrderResource retOrderResource = sut.get(2L);

        // then
        assertNull(retOrderResource);
    }

    @Test
    public void getAll() {
        // when
        final List<OrderResource> orderResources = sut.get();

        // then
        assertEquals(1, orderResources.size());
    }

    @Test
    public void getBetweenDates() {
        // when
        final List<OrderResource> orderResources = sut.get(fromDate, toDate);

        // then
        assertEquals(1, orderResources.size());
    }
}