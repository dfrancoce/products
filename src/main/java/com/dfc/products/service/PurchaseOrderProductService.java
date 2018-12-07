package com.dfc.products.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.dfc.products.model.Product;
import com.dfc.products.model.PurchaseOrder;
import com.dfc.products.model.PurchaseOrderProduct;
import com.dfc.products.model.PurchaseOrderProductId;
import com.dfc.products.repository.ProductRepository;
import com.dfc.products.repository.PurchaseOrderProductRepository;
import com.dfc.products.repository.PurchaseOrderRepository;
import com.dfc.products.service.dto.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	@Autowired
	private PurchaseOrderProductRepository purchaseOrderProductRepository;

	public void addProductsToOrder(final List<Product> products, final Long orderId) {
		final Optional<PurchaseOrder> order = purchaseOrderRepository.findById(orderId);

		order.ifPresent(purchaseOrder -> products.forEach(product -> {
			final Product dbProduct = getStoredProduct(product);
			if (Objects.nonNull(dbProduct)) {
				final PurchaseOrderProduct purchaseOrderProduct = createPurchaseOrderProduct(purchaseOrder, dbProduct);
				purchaseOrderProductRepository.save(purchaseOrderProduct);
			}
		}));
	}

	public void deleteProductsFromOrder(final List<Product> products, final Long orderId) {
		final Optional<PurchaseOrder> order = purchaseOrderRepository.findById(orderId);

		order.ifPresent(purchaseOrder -> products.forEach(product -> {
			final Product dbProduct = getStoredProduct(product);
			if (Objects.nonNull(dbProduct)) {
				final PurchaseOrderProductId purchaseOrderProductId = new PurchaseOrderProductId(purchaseOrder.getId(), dbProduct.getId());
				final Optional<PurchaseOrderProduct> purchaseOrderProduct = purchaseOrderProductRepository.findById(purchaseOrderProductId);

				purchaseOrderProduct.ifPresent(purchaseOrderProduct1 -> purchaseOrderProductRepository.delete(purchaseOrderProduct1));
			}
		}));
	}

	public List<OrderProduct> getProductsOfAnOrder(final Long orderId) {
		final Optional<PurchaseOrder> order = purchaseOrderRepository.findById(orderId);

		final List<OrderProduct> orderProducts = new ArrayList<>();
		if (order.isPresent()) {
			final List<PurchaseOrderProduct> purchaseOrderProducts = purchaseOrderProductRepository.findAllByPurchaseOrder(order.get());
			purchaseOrderProducts.forEach(purchaseOrderProduct -> {
				orderProducts.add(new OrderProduct(purchaseOrderProduct.getProduct().getName(), purchaseOrderProduct.getPrice()));
			});
		}

		return orderProducts;
	}

	public Double calculate(final Long orderId) {
		final Optional<PurchaseOrder> order = purchaseOrderRepository.findById(orderId);
		if (order.isPresent()) {
			final List<PurchaseOrderProduct> purchaseOrderProducts = purchaseOrderProductRepository.findAllByPurchaseOrder(order.get());
			return purchaseOrderProducts.stream().mapToDouble(PurchaseOrderProduct::getPrice).sum();
		}

		return 0.0;
	}

	private Product getStoredProduct(final Product product) {
		Optional<Product> dbProductById = Optional.empty();
		if (Objects.nonNull(product.getId())) {
			dbProductById = productRepository.findById(product.getId());
		}

		return dbProductById.orElseGet(() -> productRepository.findProductByName(product.getName()));
	}

	private PurchaseOrderProduct createPurchaseOrderProduct(final PurchaseOrder purchaseOrder, final Product product) {
		return new PurchaseOrderProduct(purchaseOrder, product);
	}
}
