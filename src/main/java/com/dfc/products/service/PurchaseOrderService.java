package com.dfc.products.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.dfc.products.model.PurchaseOrder;
import com.dfc.products.repository.PurchaseOrderRepository;
import com.dfc.products.service.dto.Order;
import com.dfc.products.service.dto.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	@Autowired
	private PurchaseOrderProductService purchaseOrderProductService;

	public PurchaseOrder create(final PurchaseOrder order) {
		return purchaseOrderRepository.save(order);
	}

	public PurchaseOrder update(final PurchaseOrder updatedOrder, final Long id) {
		return purchaseOrderRepository.findById(id).map(order -> {
			if (updatedOrder.getEmail() != null) order.setEmail(updatedOrder.getEmail());
			if (updatedOrder.getPurchaseOrderDate() != null) order.setPurchaseOrderDate(updatedOrder.getPurchaseOrderDate());

			return purchaseOrderRepository.save(order);
		}).orElseGet(() -> {
			updatedOrder.setId(id);
			return purchaseOrderRepository.save(updatedOrder);
		});
	}

	public void delete(final Long id) {
		purchaseOrderRepository.deleteById(id);
	}

	public Order get(final Long id) {
		final Optional<PurchaseOrder> dbOrder = purchaseOrderRepository.findById(id);

		if (dbOrder.isPresent()) {
			final List<OrderProduct> products = purchaseOrderProductService.getProductsOfAnOrder(id);
			return new Order(dbOrder.get().getEmail(), dbOrder.get().getPurchaseOrderDate(), products);
		} else {
			return null;
		}
	}

	public List<Order> get() {
		final List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
		return getOrders(purchaseOrders);
	}

	public List<Order> get(final Date fromDate, final Date toDate) {
		final List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByPurchaseOrderDateBetween(fromDate, toDate);
		return getOrders(purchaseOrders);
	}

	private List<Order> getOrders(List<PurchaseOrder> purchaseOrders) {
		final List<Order> orders = new ArrayList<>();

		purchaseOrders.forEach(purchaseOrder -> {
			final List<OrderProduct> products = purchaseOrderProductService.getProductsOfAnOrder(purchaseOrder.getId());
			orders.add(new Order(purchaseOrder.getEmail(), purchaseOrder.getPurchaseOrderDate(), products));
		});

		return orders;
	}
}
