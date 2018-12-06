package com.dfc.products.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class PurchaseOrderProductId implements Serializable {
	private Long purchaseOrderId;
	private Long productId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PurchaseOrderProductId that = (PurchaseOrderProductId) o;
		return Objects.equals(purchaseOrderId, that.purchaseOrderId) &&
				Objects.equals(productId, that.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(purchaseOrderId, productId);
	}
}
