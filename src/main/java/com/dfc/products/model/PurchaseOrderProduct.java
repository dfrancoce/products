package com.dfc.products.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class PurchaseOrderProduct {
	@EmbeddedId
	private PurchaseOrderProductId id = new PurchaseOrderProductId();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("purchaseOrderId")
	private PurchaseOrder purchaseOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	private Product product;

	@Column(name = "price")
	private Double price;

	public PurchaseOrderProduct() {
	}

	public PurchaseOrderProduct(PurchaseOrder purchaseOrder, Product product) {
		this.purchaseOrder = purchaseOrder;
		this.product = product;
		this.price = product.getPrice();
	}

	public PurchaseOrderProductId getId() {
		return id;
	}

	public void setId(PurchaseOrderProductId id) {
		this.id = id;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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