package com.dfc.products.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.dfc.products.model.Product;
import com.dfc.products.model.Order;
import com.dfc.products.model.OrderProduct;
import com.dfc.products.model.OrderProductId;
import com.dfc.products.repository.ProductRepository;
import com.dfc.products.repository.OrderProductRepository;
import com.dfc.products.repository.OrderRepository;
import com.dfc.products.service.mapper.ProductMapper;
import com.dfc.products.service.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductMapper productMapper;

    @Autowired
    public OrderProductService(ProductRepository productRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productMapper = productMapper;
    }

    public void addProductsToOrder(final List<ProductResource> productResources, final Long orderId) {
        final Optional<Order> order = orderRepository.findById(orderId);

        order.ifPresent(orderObject -> productResources.forEach(productResource -> {
            final Product dbProduct = getStoredProduct(productMapper.map(productResource));

            if (Objects.nonNull(dbProduct)) {
                final OrderProduct orderProduct = createOrderProduct(orderObject, dbProduct);
                orderProductRepository.save(orderProduct);
            }
        }));
    }

    public void deleteProductsFromOrder(final List<ProductResource> productResources, final Long orderId) {
        final Optional<Order> order = orderRepository.findById(orderId);

        order.ifPresent(orderObject -> productResources.forEach(productResource -> {
            final Product dbProduct = getStoredProduct(productMapper.map(productResource));

            if (Objects.nonNull(dbProduct)) {
                final OrderProductId orderProductId = new OrderProductId(orderObject.getId(), dbProduct.getId());
                final Optional<OrderProduct> orderProduct = orderProductRepository.findById(orderProductId);

                orderProduct.ifPresent(orderProductRepository::delete);
            }
        }));
    }

    public Double calculate(final Long orderId) {
        final Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            final List<OrderProduct> orderProducts = orderProductRepository.findAllByOrder(order.get());
            return orderProducts.stream().mapToDouble(OrderProduct::getPrice).sum();
        }

        return 0.0;
    }

    List<ProductResource> getProductsOfAnOrder(final Long orderId) {
        final Optional<Order> order = orderRepository.findById(orderId);
        final List<ProductResource> orderProducts = new ArrayList<>();

        if (order.isPresent()) {
            final List<OrderProduct> storedOrderProducts = orderProductRepository.findAllByOrder(order.get());
            storedOrderProducts.forEach(orderProduct ->
                    orderProducts.add(productMapper.map(orderProduct.getProduct(), orderProduct.getPrice()))
            );
        }

        return orderProducts;
    }

    private Product getStoredProduct(final Product product) {
        Optional<Product> dbProductById = Optional.empty();

        if (Objects.nonNull(product.getId())) {
            dbProductById = productRepository.findById(product.getId());
        }

        return dbProductById.orElseGet(() -> productRepository.findProductByName(product.getName()));
    }

    private OrderProduct createOrderProduct(final Order order, final Product product) {
        return new OrderProduct(order, product);
    }
}
