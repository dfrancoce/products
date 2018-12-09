package com.dfc.products.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "orders_products", schema = "public")
public class OrderProduct {
	@EmbeddedId
	private OrderProductId id = new OrderProductId();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("orderId")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	private Product product;

	@Column(name = "price")
	private Double price;

	public OrderProduct() {
	}

	public OrderProduct(Order order, Product product) {
		this.order = order;
		this.product = product;
		this.price = product.getPrice();
	}

	public OrderProductId getId() {
		return id;
	}

	public void setId(OrderProductId id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}