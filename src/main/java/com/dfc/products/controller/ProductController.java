package com.dfc.products.controller;

import java.util.List;

import com.dfc.products.service.ProductService;
import com.dfc.products.service.resource.ProductResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Products", description = "Set of endpoints for performing operations with products.")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation("Creates a new product in the system.")
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResource create(@RequestBody final ProductResource productResource) {
        return productService.create(productResource);
    }

    @ApiOperation("Retrieves all the products stored in the system.")
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResource> get() {
        return productService.get();
    }

    @ApiOperation("Retrieves the product with the id passed in the path.")
    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResource get(@PathVariable final Long id) {
        return productService.get(id);
    }

    @ApiOperation("Updates the product with the id passed in the path.")
    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResource update(@RequestBody ProductResource productResource, @PathVariable final Long id) {
        return productService.update(productResource, id);
    }

    @ApiOperation("Deletes from the system the product with the id passed in the path.")
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final Long id) {
        productService.delete(id);
    }
}
