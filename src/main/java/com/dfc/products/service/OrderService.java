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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class contains the business logic to perform the different operations with orders
 */
@Service
public class OrderService {
    private final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderProductService orderProductService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderProductService orderProductService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderProductService = orderProductService;
        this.orderMapper = orderMapper;
    }

    public OrderResource create(final OrderResource orderResource) {
        LOG.debug("OrderService - create - start");

        final Order dbOrder = orderRepository.save(orderMapper.map(orderResource));
        return orderMapper.map(dbOrder);
    }

    public OrderResource update(final OrderResource updatedOrderResource, final Long id) {
        LOG.debug("OrderService - update - start");

        return orderRepository.findById(id).map(order -> {
            if (updatedOrderResource.getEmail() != null) order.setEmail(updatedOrderResource.getEmail());
            if (updatedOrderResource.getOrderDate() != null) {
                order.setOrderDate(updatedOrderResource.getOrderDate());
            }

            final Order dbOrder = orderRepository.save(order);
            return orderMapper.map(dbOrder);
        }).orElseGet(() -> { // if the order doesn't exist in the database
            LOG.warn("OrderService - update - Order with id = {} not found", id);

            final Order order = orderMapper.map(updatedOrderResource);
            order.setId(id);
            final Order dbOrder = orderRepository.save(order);
            return orderMapper.map(dbOrder);
        });
    }

    public void delete(final Long id) {
        LOG.debug("OrderService - delete - start");

        orderRepository.deleteById(id);
    }

    public OrderResource get(final Long id) {
        LOG.debug("OrderService - get({}) - start", id);

        final Optional<Order> dbOrder = orderRepository.findById(id);
        if (dbOrder.isPresent()) {
            final List<ProductResource> products = orderProductService.getProductsOfAnOrder(id);
            final OrderResource orderResource = orderMapper.map(dbOrder.get());
            orderResource.setProducts(products);

            return orderResource;
        } else {
            LOG.warn("OrderService - get({}) - Order not found", id);
            return null;
        }
    }

    public List<OrderResource> get() {
        LOG.debug("OrderService - get - start");

        final List<Order> orders = orderRepository.findAll();
        return getOrders(orders);
    }

    public List<OrderResource> get(final Date fromDate, final Date toDate) {
        LOG.debug("OrderService - get({}, {}) - start", fromDate, toDate);

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
