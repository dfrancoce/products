package com.dfc.products.controller;

import com.dfc.products.model.Product;
import com.dfc.products.model.PurchaseOrder;
import com.dfc.products.service.PurchaseOrderProductService;
import com.dfc.products.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private PurchaseOrderProductService purchaseOrderProductService;

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrder create(@RequestBody final PurchaseOrder order) {
        return purchaseOrderService.create(order);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseOrder> get() {
        return purchaseOrderService.get();
    }

    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseOrder get(@PathVariable final Long id) {
        return purchaseOrderService.get(id);
    }

    @PutMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseOrder update(@RequestBody PurchaseOrder order, @PathVariable final Long id) {
        return purchaseOrderService.update(order, id);
    }

    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable final Long id) {
        purchaseOrderService.delete(id);
    }

    @PutMapping("/orders/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public void addProducts(@RequestBody List<Product> products, @PathVariable final Long id) {
        purchaseOrderProductService.addProductsToOrder(products, id);
    }

    @DeleteMapping("/orders/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProducts(@RequestBody List<Product> products, @PathVariable final Long id) {
        purchaseOrderProductService.deleteProductsFromOrder(products, id);
    }

    @GetMapping("/orders/{id}/calculate")
    @ResponseStatus(HttpStatus.OK)
    public Double calculate(@PathVariable final Long id) {
        return purchaseOrderProductService.calculate(id);
    }
}
