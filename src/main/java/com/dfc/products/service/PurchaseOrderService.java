package com.dfc.products.service;

import com.dfc.products.model.PurchaseOrder;
import com.dfc.products.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

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

    public PurchaseOrder get(final Long id) {
        final Optional<PurchaseOrder> dbOrder = purchaseOrderRepository.findById(id);
        return dbOrder.orElse(null);
    }

    public List<PurchaseOrder> get() {
        return purchaseOrderRepository.findAll();
    }
}
