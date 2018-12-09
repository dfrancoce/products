package com.dfc.products.service.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResource {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String name;
	@JsonProperty
	private Double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
