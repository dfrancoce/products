package com.dfc.products.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products", schema = "public")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
	@SequenceGenerator(name="product_generator", sequenceName = "product_seq")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "price")
	private Double price;
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
	private List<OrderProduct> orders;

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

	public List<OrderProduct> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderProduct> orders) {
		this.orders = orders;
	}
}
