package com.dfc.products.controller;

import com.dfc.products.service.OrderProductService;
import com.dfc.products.service.OrderService;
import com.dfc.products.service.resource.OrderResource;
import com.dfc.products.service.resource.ProductResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "Orders", description = "Set of endpoints for performing operations with orders.")
public class OrderController {
    private final OrderService orderService;
    private final OrderProductService orderProductService;

    @Autowired
    public OrderController(OrderService orderService, OrderProductService orderProductService) {
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @ApiOperation("Creates a new order in the system.")
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResource create(@RequestBody final OrderResource orderResource) {
        return orderService.create(orderResource);
    }

    @ApiOperation("Retrieves all the orders stored in the system.")
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResource> get() {
        return orderService.get();
    }

    @ApiOperation("Retrieves all the orders, between the dates passed by parameter, stored in the system.")
    @GetMapping("/orders/period")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResource> get(@RequestParam(value = "fromDate") @DateTimeFormat(pattern = "ddMMyyyy") Date fromDate,
                                   @RequestParam(value = "toDate") @DateTimeFormat(pattern = "ddMMyyyy") Date toDate) {
        return orderService.get(fromDate, toDate);
    }

    @ApiOperation("Retrieves the order with the id passed in the path.")
    @GetMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResource get(@PathVariable final Long id) {
        return orderService.get(id);
    }

    @ApiOperation("Updates the order with the id passed in the path.")
    @PutMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResource update(@RequestBody OrderResource orderResource, @PathVariable final Long id) {
        return orderService.update(orderResource, id);
    }

    @ApiOperation("Deletes from the system the order with the id passed in the path.")
    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final Long id) {
        orderService.delete(id);
    }

    @ApiOperation("Adds the products passed in the body to the order with the id passed in the path.")
    @PutMapping("/orders/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public void addProducts(@RequestBody List<ProductResource> products, @PathVariable final Long id) {
        orderProductService.addProductsToOrder(products, id);
    }

    @ApiOperation("Deletes the products passed in the body from the order with the id passed in the path.")
    @DeleteMapping("/orders/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProducts(@RequestBody List<ProductResource> products, @PathVariable final Long id) {
        orderProductService.deleteProductsFromOrder(products, id);
    }

    @ApiOperation("Calculates the total for the products of the order with the id passed in the path.")
    @GetMapping("/orders/{id}/calculate")
    @ResponseStatus(HttpStatus.OK)
    public String calculate(@PathVariable final Long id) {
        final Double total = orderProductService.calculate(id);
        return "{ \"total\": \"" + total + "\" }";
    }
}
