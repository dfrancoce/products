package com.dfc.products.controller;

import java.util.Date;
import java.util.List;

import com.dfc.products.model.Product;
import com.dfc.products.model.PurchaseOrder;
import com.dfc.products.service.PurchaseOrderProductService;
import com.dfc.products.service.PurchaseOrderService;
import com.dfc.products.service.dto.Order;
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

@RestController
@RequestMapping("/api")
@Api(tags = "Orders", description = "Set of endpoints for performing operations with orders.")
public class PurchaseOrderController {
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	@Autowired
	private PurchaseOrderProductService purchaseOrderProductService;

	@ApiOperation("Creates a new order in the system.")
	@PostMapping("/orders")
	@ResponseStatus(HttpStatus.CREATED)
	public PurchaseOrder create(@RequestBody final PurchaseOrder order) {
		return purchaseOrderService.create(order);
	}

	@ApiOperation("Retrieves all the orders stored in the system.")
	@GetMapping("/orders")
	@ResponseStatus(HttpStatus.OK)
	public List<Order> get() {
		return purchaseOrderService.get();
	}

	@ApiOperation("Retrieves all the orders, between the dates passed by parameter, stored in the system.")
	@GetMapping("/orders/period")
	@ResponseStatus(HttpStatus.OK)
	public List<Order> get(@RequestParam(value = "fromDate") @DateTimeFormat(pattern = "ddMMyyyy") Date fromDate,
	                       @RequestParam(value = "toDate") @DateTimeFormat(pattern = "ddMMyyyy") Date toDate) {
		return purchaseOrderService.get(fromDate, toDate);
	}

	@ApiOperation("Retrieves the order with the id passed in the path.")
	@GetMapping("/orders/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Order get(@PathVariable final Long id) {
		return purchaseOrderService.get(id);
	}

	@ApiOperation("Updates the order with the id passed in the path.")
	@PutMapping("/orders/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PurchaseOrder update(@RequestBody PurchaseOrder order, @PathVariable final Long id) {
		return purchaseOrderService.update(order, id);
	}

	@ApiOperation("Deletes from the system the order with the id passed in the path.")
	@DeleteMapping("/orders/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable final Long id) {
		purchaseOrderService.delete(id);
	}

	@ApiOperation("Adds the products passed in the body to the order with the id passed in the path.")
	@PutMapping("/orders/{id}/products")
	@ResponseStatus(HttpStatus.OK)
	public void addProducts(@RequestBody List<Product> products, @PathVariable final Long id) {
		purchaseOrderProductService.addProductsToOrder(products, id);
	}

	@ApiOperation("Deletes the products passed in the body from the order with the id passed in the path.")
	@DeleteMapping("/orders/{id}/products")
	@ResponseStatus(HttpStatus.OK)
	public void deleteProducts(@RequestBody List<Product> products, @PathVariable final Long id) {
		purchaseOrderProductService.deleteProductsFromOrder(products, id);
	}

	@ApiOperation("Calculates the total for the products of the order with the id passed in the path.")
	@GetMapping("/orders/{id}/calculate")
	@ResponseStatus(HttpStatus.OK)
	public String calculate(@PathVariable final Long id) {
		final Double total = purchaseOrderProductService.calculate(id);
		return "{ \"total\": \"" + total + "\" }";
	}
}
