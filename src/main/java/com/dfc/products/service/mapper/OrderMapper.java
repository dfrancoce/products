package com.dfc.products.service.mapper;

import com.dfc.products.model.Order;
import com.dfc.products.service.resource.OrderResource;
import org.springframework.stereotype.Component;

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
		order.setEmail(orderResource.getEmail());
		order.setOrderDate(orderResource.getOrderDate());

		return order;
	}
}
