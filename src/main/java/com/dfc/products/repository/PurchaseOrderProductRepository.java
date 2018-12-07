package com.dfc.products.repository;

import com.dfc.products.model.PurchaseOrder;
import com.dfc.products.model.PurchaseOrderProduct;
import com.dfc.products.model.PurchaseOrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderProductRepository extends JpaRepository<PurchaseOrderProduct, PurchaseOrderProductId>  {
    List<PurchaseOrderProduct> findAllByPurchaseOrder(final PurchaseOrder purchaseOrder);
}