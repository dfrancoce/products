package com.dfc.products.service.mapper;

import com.dfc.products.model.Product;
import com.dfc.products.service.resource.ProductResource;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
	public ProductResource map(final Product product) {
		final ProductResource productResource = new ProductResource();
		productResource.setId(product.getId());
		productResource.setName(product.getName());
		productResource.setPrice(product.getPrice());

		return productResource;
	}

	public ProductResource map(final Product product, final Double price) {
		final ProductResource productResource = new ProductResource();
		productResource.setId(product.getId());
		productResource.setName(product.getName());
		productResource.setPrice(price);

		return productResource;
	}

	public Product map(final ProductResource productResource) {
		final Product product = new Product();
		product.setName(productResource.getName());
		product.setPrice(productResource.getPrice());

		return product;
	}
}
