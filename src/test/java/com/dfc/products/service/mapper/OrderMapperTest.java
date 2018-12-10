package com.dfc.products.service.mapper;

import com.dfc.products.model.Order;
import com.dfc.products.service.resource.OrderResource;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class OrderMapperTest {
    private OrderMapper sut;

    @Before
    public void setUp() {
        sut = new OrderMapper();
    }

    @Test
    public void mapToOrderResource() {
        final Order order = getOrder();
        final OrderResource orderResource = sut.map(order);

        assertEquals(orderResource.getId(), order.getId());
        assertEquals(orderResource.getEmail(), order.getEmail());
        assertEquals(orderResource.getOrderDate(), order.getOrderDate());
    }

    @Test
    public void mapToOrder() {
        final OrderResource orderResource = getOrderResource();
        final Order order = sut.map(orderResource);

        assertEquals(order.getId(), orderResource.getId());
        assertEquals(order.getEmail(), orderResource.getEmail());
        assertEquals(order.getOrderDate(), orderResource.getOrderDate());
    }

    private Order getOrder() {
        final Order order = new Order();
        order.setId(1L);
        order.setEmail("test@gmail.com");

        final Date orderDate = Date
                .from(LocalDate.of(2018, 12, 10)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());
        order.setOrderDate(orderDate);

        return order;
    }

    private OrderResource getOrderResource() {
        final OrderResource orderResource = new OrderResource();
        orderResource.setId(1L);
        orderResource.setEmail("test@gmail.com");

        final Date orderDate = Date
                .from(LocalDate.of(2018, 12, 10)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());
        orderResource.setOrderDate(orderDate);

        return orderResource;
    }
}