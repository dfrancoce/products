package com.dfc.products.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.dfc.products.model.Order;
import com.dfc.products.repository.OrderRepository;
import com.dfc.products.service.mapper.OrderMapper;
import com.dfc.products.service.resource.OrderResource;
import com.dfc.products.service.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderProductService orderProductService;
	@Autowired
	private OrderMapper orderMapper;

	public OrderResource create(final OrderResource orderResource) {
		final Order dbOrder = orderRepository.save(orderMapper.map(orderResource));
		return orderMapper.map(dbOrder);
	}

	public OrderResource update(final OrderResource updatedOrderResource, final Long id) {
		return orderRepository.findById(id).map(order -> {
			if (updatedOrderResource.getEmail() != null) order.setEmail(updatedOrderResource.getEmail());
			if (updatedOrderResource.getOrderDate() != null) {
				order.setOrderDate(updatedOrderResource.getOrderDate());
			}

			final Order dbOrder = orderRepository.save(order);
			return orderMapper.map(dbOrder);
		}).orElseGet(() -> {
			final Order order = orderMapper.map(updatedOrderResource);
			order.setId(id);
			
			final Order dbOrder = orderRepository.save(order);
			return orderMapper.map(dbOrder);
		});
	}

	public void delete(final Long id) {
		orderRepository.deleteById(id);
	}

	public OrderResource get(final Long id) {
		final Optional<Order> dbOrder = orderRepository.findById(id);

		if (dbOrder.isPresent()) {
			final List<ProductResource> products = orderProductService.getProductsOfAnOrder(id);
			final OrderResource orderResource = orderMapper.map(dbOrder.get());
			orderResource.setProducts(products);

			return orderResource;
		} else {
			return null;
		}
	}

	public List<OrderResource> get() {
		final List<Order> orders = orderRepository.findAll();
		return getOrders(orders);
	}

	public List<OrderResource> get(final Date fromDate, final Date toDate) {
		final List<Order> orders = orderRepository.findByOrderDateBetween(fromDate, toDate);
		return getOrders(orders);
	}

	private List<OrderResource> getOrders(List<Order> storedOrders) {
		final List<OrderResource> orders = new ArrayList<>();

		storedOrders.forEach(order -> {
			final List<ProductResource> products = orderProductService.getProductsOfAnOrder(order.getId());
			final OrderResource orderResource = orderMapper.map(order);
			orderResource.setProducts(products);

			orders.add(orderResource);
		});

		return orders;
	}
}
