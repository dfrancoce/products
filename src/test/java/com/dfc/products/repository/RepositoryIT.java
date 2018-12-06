package com.dfc.products.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.dfc.products.model.Product;
import com.dfc.products.model.PurchaseOrder;
import com.dfc.products.model.PurchaseOrderProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryIT {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseOrderProductRepository purchaseOrderProductRepository;

    @Test
    public void insertOrder() {
        // given
        assertEquals(0, purchaseOrderRepository.findAll().size());

        // when
        final PurchaseOrder purchaseOrder = getOrder("test@gmail.com");
        purchaseOrderRepository.save(purchaseOrder);

        // then
        assertEquals(1, purchaseOrderRepository.findAll().size());
    }

    @Test
    public void updateOrder() {
        // given
        final PurchaseOrder purchaseOrder = getOrder("test@gmail.com");
        purchaseOrderRepository.save(purchaseOrder);

        // when
        PurchaseOrder retrievedPurchaseOrder = purchaseOrderRepository.findAll().get(0);
        retrievedPurchaseOrder.setEmail("updated@gmail.com");
        purchaseOrderRepository.save(retrievedPurchaseOrder);

        // then
        retrievedPurchaseOrder = purchaseOrderRepository.findAll().get(0);
        assertEquals("updated@gmail.com", retrievedPurchaseOrder.getEmail());
    }

    @Test
    public void deleteOrder() {
        // given
        final PurchaseOrder purchaseOrder = getOrder("test@gmail.com");
        purchaseOrderRepository.save(purchaseOrder);
        assertEquals(1, purchaseOrderRepository.findAll().size());

        // when
        purchaseOrderRepository.delete(purchaseOrder);

        // then
        assertEquals(0, purchaseOrderRepository.findAll().size());
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
    public void addProductsToAnOrder() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);
        final Product iphone = getProduct("Apple Iphone", 1000.0);
        productRepository.save(iphone);

        // when
        final PurchaseOrder purchaseOrder = getOrder("applefan@gmail.com");
        purchaseOrderRepository.save(purchaseOrder);
        final PurchaseOrderProduct firstPurchaseOrderProduct = new PurchaseOrderProduct(purchaseOrder, macbook);
        purchaseOrderProductRepository.save(firstPurchaseOrderProduct);
        final PurchaseOrderProduct secondPurchaseOrderProduct = new PurchaseOrderProduct(purchaseOrder, iphone);
        purchaseOrderProductRepository.save(secondPurchaseOrderProduct);

        // then
        assertEquals(2, productRepository.findAll().size());
        assertEquals(1, purchaseOrderRepository.findAll().size());
        assertEquals(2, purchaseOrderProductRepository.findAll().size());
    }

    @Test
    public void checkUpdatedProductPriceDoesntAffectOrder() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);

        // when
        final PurchaseOrder purchaseOrder = getOrder("applefan@gmail.com");
        purchaseOrderRepository.save(purchaseOrder);
        final PurchaseOrderProduct firstPurchaseOrderProduct = new PurchaseOrderProduct(purchaseOrder, macbook);
        purchaseOrderProductRepository.save(firstPurchaseOrderProduct);

        // then
        final Product retrievedProduct = productRepository.findAll().get(0);
        retrievedProduct.setPrice(1000.0);
        productRepository.save(retrievedProduct);

        final PurchaseOrderProduct purchaseOrderProduct = purchaseOrderProductRepository.getOne(firstPurchaseOrderProduct.getId());
        assertEquals(2000.00, purchaseOrderProduct.getPrice(), 0);
    }

    @Test
    public void deleteOrderWithProducts() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);
        final PurchaseOrder purchaseOrder = getOrder("applefan@gmail.com");
        purchaseOrderRepository.save(purchaseOrder);
        final PurchaseOrderProduct firstPurchaseOrderProduct = new PurchaseOrderProduct(purchaseOrder, macbook);
        purchaseOrderProductRepository.save(firstPurchaseOrderProduct);

        // when
        purchaseOrderProductRepository.deleteAll();
        purchaseOrderRepository.delete(purchaseOrder);

        // then
        assertEquals(1, productRepository.findAll().size());
        assertEquals(0, purchaseOrderRepository.findAll().size());
        assertEquals(0, purchaseOrderProductRepository.findAll().size());
    }

    @Test
    public void deleteProductsInOrders() {
        // given
        final Product macbook = getProduct("Apple Macbook", 2000.00);
        productRepository.save(macbook);
        final PurchaseOrder purchaseOrder = getOrder("applefan@gmail.com");
        purchaseOrderRepository.save(purchaseOrder);
        final PurchaseOrderProduct firstPurchaseOrderProduct = new PurchaseOrderProduct(purchaseOrder, macbook);
        purchaseOrderProductRepository.save(firstPurchaseOrderProduct);

        // when
        purchaseOrderProductRepository.deleteAll();
        productRepository.delete(macbook);

        // then
        assertEquals(1, purchaseOrderRepository.findAll().size());
        assertEquals(0, productRepository.findAll().size());
        assertEquals(0, purchaseOrderProductRepository.findAll().size());
    }

    private Product getProduct(final String name, final Double price) {
        final Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        return product;
    }

    private PurchaseOrder getOrder(final String email) {
        final PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setEmail(email);
        purchaseOrder.setPurchaseOrderDate(new Date());

        return purchaseOrder;
    }
}
