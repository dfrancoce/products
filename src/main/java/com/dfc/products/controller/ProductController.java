package com.dfc.products.controller;

import java.util.List;

import com.dfc.products.model.Product;
import com.dfc.products.service.ProductService;
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

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody final Product product) {
		return productService.create(product);
	}

	@GetMapping("/products")
	@ResponseStatus(HttpStatus.OK)
	public List<Product> get() {
		return productService.get();
	}

	@GetMapping("/products/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Product get(@PathVariable final Long id) {
		return productService.get(id);
	}

	@PutMapping("/products/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Product update(@RequestBody Product product, @PathVariable final Long id) {
		return productService.update(product, id);
	}

	@DeleteMapping("/products/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable final Long id) {
		productService.delete(id);
	}
}
