package com.dfc.products.repository;

import java.util.List;

import com.dfc.products.model.Order;
import com.dfc.products.model.OrderProduct;
import com.dfc.products.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
	List<OrderProduct> findAllByOrder(final Order order);
}