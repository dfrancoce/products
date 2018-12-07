package com.dfc.products.repository;

import java.util.List;

import com.dfc.products.model.PurchaseOrder;
import com.dfc.products.model.PurchaseOrderProduct;
import com.dfc.products.model.PurchaseOrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderProductRepository extends JpaRepository<PurchaseOrderProduct, PurchaseOrderProductId> {
	List<PurchaseOrderProduct> findAllByPurchaseOrder(final PurchaseOrder purchaseOrder);
}