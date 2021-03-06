package com.dfc.products.service.mapper;

import com.dfc.products.model.Order;
import com.dfc.products.service.resource.OrderResource;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for converting Order to OrderResource objects and the other way around
 */
@Component
public class OrderMapper {
    public OrderResource map(final Order order) {
        final OrderResource orderResource = new OrderResource();
        orderResource.setId(order.getId());
        orderResource.setEmail(order.getEmail());
        orderResource.setOrderDate(order.getOrderDate());

        return orderResource;
    }

    public Order map(final OrderResource orderResource) {
        final Order order = new Order();
        order.setId(orderResource.getId());
        order.setEmail(orderResource.getEmail());
        order.setOrderDate(orderResource.getOrderDate());

        return order;
    }
}
