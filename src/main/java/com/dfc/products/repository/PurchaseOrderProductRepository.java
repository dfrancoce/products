package com.dfc.products.repository;

import com.dfc.products.model.PurchaseOrderProduct;
import com.dfc.products.model.PurchaseOrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderProductRepository extends JpaRepository<PurchaseOrderProduct, PurchaseOrderProductId>  {
}