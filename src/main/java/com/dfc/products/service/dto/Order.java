package com.dfc.products.service.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Order {
	@JsonProperty
	private String email;
	@JsonProperty
	private Date date;
	@JsonProperty
	private List<OrderProduct> products;

	public Order(String email, Date date, List<OrderProduct> products) {
		this.email = email;
		this.date = date;
		this.products = products;
	}
}
