package com.dfc.products.repository;

import java.util.Date;
import java.util.List;

import com.dfc.products.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderDateBetween(final Date fromDate, final Date toDate);
}
