package com.dfc.products.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.dfc.products.model.Order;
import com.dfc.products.model.OrderProduct;
import com.dfc.products.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryIT {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Test
    public void insertOrder() {
        // given
        assertEquals(0, orderRepository.findAll().size());

        // when
        final Order order = getOrder("test@gmail.com");
        orderRepository.save(order);

        // then
        assertEquals(1, orderRepository.findAll().size());
    }

    @Test
    public void updateOrder() {
        // given
        final Order order = getOrder("test@gmail.com");
        orderRepository.save(order);

        // when
        Order retrievedOrder = orderRepository.findAll().get(0);
        retrievedOrder.setEmail("updated@gmail.com");
        orderRepository.save(retrievedOrder);

        // then
        retrievedOrder = orderRepository.findAll().get(0);
        assertEquals("updated@gmail.com", retrievedOrder.getEmail());
    }

    @Test
    public void deleteOrder() {
        // given
        final Order order = getOrder("test@gmail.com");
        orderRepository.save(order);
        assertEquals(1, orderRepository.findAll().size());

        // when
        orderRepository.delete(order);

        // then
        assertEquals(0, orderRepository.findAll().size());
    }

    @Test
    public void findByOrderDateBetween() {
        // given
        final Order order = getOrder("test@gmail.com");
        order.setOrderDate(Date.from(LocalDate.of(2018, 12, 9).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        orderRepository.save(order);
        assertEquals(1, orderRepository.findAll().size());

        // when
        final Date fromDate = Date.from(LocalDate.of(2018, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        final Date toDate = Date.from(LocalDate.of(2018, 12, 10).atStartOfDay(ZoneId.systemDefault()).toInstant());
        final List<Order> orders = orderRepository.findByOrderDateBetween(fromDate, toDate);

        // then
        assertEquals(1, orders.size());
    }

    @Test
    public void insertProduct() {
        // given
        assertEquals(0, productRepository.findAll().size());

        // when
        final Product product = getProduct("Apple Macbook", 2000.00);
        productRepository.save(product);

        // then
        assertEquals(1, productRepository.findAll().size());
    }

    @Test
    public void updateProduct() {
        // given
        final Product product = getProduct("Apple Macbook", 2000.00);
        productRepository.save(product);

        // when
        Product retrievedProduct = productRepository.findAll().get(0);
        retrievedProduct.setPrice(1000.0);
        productRepository.save(retrievedProduct);

        // then
        retrievedProduct = productRepository.findAll().get(0);
        assertEquals(1000.0, retrievedProduct.getPrice(), 0);
    }

    @Test
    public void deleteProduct() {
        // given
        final Product product = getProduct("Apple Macbook", 2000.00);
        productRepository.save(product);
        assertEquals(1, productRepository.findAll().size());

        // when
        productRepository.delete(product);

        // then
        assertEquals(0, productRepository.findAll().size());
    }

    @Test
    public void findProductByName() {
        // given
        final Product product = getProduct("Apple Macbook", 2000.00);
        productRepository.save(product);
        assertEquals(1, productRepository.findAll().size());

        // when
        final Product dbProduct = productRepository.findProductByName("Apple Macbook");

        // then
        assertEquals("Apple Macbook", dbProduct.getName());
    }

    @Test
    public void addProductsToAnOrder() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);
        final Product iphone = getProduct("Apple Iphone", 1000.0);
        productRepository.save(iphone);

        // when
        final Order order = getOrder("applefan@gmail.com");
        orderRepository.save(order);
        final OrderProduct firstOrderProduct = new OrderProduct(order, macbook);
        orderProductRepository.save(firstOrderProduct);
        final OrderProduct secondOrderProduct = new OrderProduct(order, iphone);
        orderProductRepository.save(secondOrderProduct);

        // then
        assertEquals(2, productRepository.findAll().size());
        assertEquals(1, orderRepository.findAll().size());
        assertEquals(2, orderProductRepository.findAll().size());
    }

    @Test
    public void checkUpdatedProductPriceDoesntAffectOrder() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);

        // when
        final Order order = getOrder("applefan@gmail.com");
        orderRepository.save(order);
        final OrderProduct firstOrderProduct = new OrderProduct(order, macbook);
        orderProductRepository.save(firstOrderProduct);

        // then
        final Product retrievedProduct = productRepository.findAll().get(0);
        retrievedProduct.setPrice(1000.0);
        productRepository.save(retrievedProduct);

        final OrderProduct orderProduct = orderProductRepository.getOne(firstOrderProduct.getId());
        assertEquals(2000.00, orderProduct.getPrice(), 0);
    }

    @Test
    public void deleteOrderWithProducts() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);
        final Order order = getOrder("applefan@gmail.com");
        orderRepository.save(order);
        final OrderProduct firstOrderProduct = new OrderProduct(order, macbook);
        orderProductRepository.save(firstOrderProduct);

        // when
        orderProductRepository.deleteAll();
        orderRepository.delete(order);

        // then
        assertEquals(1, productRepository.findAll().size());
        assertEquals(0, orderRepository.findAll().size());
        assertEquals(0, orderProductRepository.findAll().size());
    }

    @Test
    public void deleteProductsInOrders() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);
        final Order order = getOrder("applefan@gmail.com");
        orderRepository.save(order);
        final OrderProduct firstOrderProduct = new OrderProduct(order, macbook);
        orderProductRepository.save(firstOrderProduct);

        // when
        orderProductRepository.deleteAll();
        productRepository.delete(macbook);

        // then
        assertEquals(1, orderRepository.findAll().size());
        assertEquals(0, productRepository.findAll().size());
        assertEquals(0, orderProductRepository.findAll().size());
    }

    @Test
    public void findAllByOrder() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);
        final Order order = getOrder("applefan@gmail.com");
        orderRepository.save(order);
        final OrderProduct orderProduct = new OrderProduct(order, macbook);
        orderProductRepository.save(orderProduct);

        // when
        final List<OrderProduct> orderProducts = orderProductRepository.findAllByOrder(order);

        // then
        assertEquals(1, orderProducts.size());
    }

    private Product getProduct(final String name, final Double price) {
        final Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        return product;
    }

    private Order getOrder(final String email) {
        final Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(new Date());

        return order;
    }
}
