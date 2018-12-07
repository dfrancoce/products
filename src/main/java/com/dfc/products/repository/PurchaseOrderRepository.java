package com.dfc.products.repository;

import java.util.Date;
import java.util.List;

import com.dfc.products.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
	List<PurchaseOrder> findByPurchaseOrderDateBetween(final Date fromDate, final Date toDate);
}
