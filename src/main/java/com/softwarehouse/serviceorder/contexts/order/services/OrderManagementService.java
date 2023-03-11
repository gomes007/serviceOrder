package com.softwarehouse.serviceorder.contexts.order.services;

import com.softwarehouse.serviceorder.contexts.order.entities.OrderEquipment;
import com.softwarehouse.serviceorder.contexts.order.entities.OrderService;
import com.softwarehouse.serviceorder.contexts.order.entities.Order;
import com.softwarehouse.serviceorder.contexts.order.entities.OrderProduct;
import com.softwarehouse.serviceorder.contexts.order.models.OrderStatus;
import com.softwarehouse.serviceorder.contexts.payment.entities.Payment;
import com.softwarehouse.serviceorder.contexts.product.entities.Product;
import com.softwarehouse.serviceorder.contexts.product.services.InventoryService;
import com.softwarehouse.serviceorder.contexts.product.services.ProductService;
import com.softwarehouse.serviceorder.contexts.service.services.ServiceService;
import com.softwarehouse.serviceorder.contexts.shared.entities.Attachment;
import com.softwarehouse.serviceorder.contexts.shared.entities.Price;
import com.softwarehouse.serviceorder.contexts.shared.services.AttachmentService;
import com.softwarehouse.serviceorder.exceptions.impl.BadRequestException;
import com.softwarehouse.serviceorder.exceptions.impl.InventoryValidationException;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.contexts.product.models.InventoryValidation;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import com.softwarehouse.serviceorder.contexts.order.repositories.OrderRepository;
import com.softwarehouse.serviceorder.contexts.product.utils.InventoryValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderManagementService {
    private final OrderRepository repository;
    private final AttachmentService attachmentService;
    private final InventoryService inventoryService;
    private final ProductService productService;
    private final ServiceService serviceService;

    public OrderManagementService(
            final OrderRepository repository,
            final AttachmentService attachmentService,
            final InventoryService inventoryService,
            final ProductService productService,
            final ServiceService serviceService
    ) {
        this.repository = repository;
        this.attachmentService = attachmentService;
        this.inventoryService = inventoryService;
        this.productService = productService;
        this.serviceService = serviceService;
    }

    public Order open(final Order order) {
        this.fetchProducts(order);
        this.fetchServices(order);
        this.validateInventory(order);
        this.calculateTotals(order);

        order.setStatus(OrderStatus.OPEN);
        order.getPayments().forEach(payment -> payment.getPayment().setAccountsPlan(order.getAccountsPlan()));

        var createdOrder = this.repository.save(order);

        createdOrder.getOrderProducts().forEach(product -> this.inventoryService.subtractAmount(
                product.getProduct().getInventory().getId(),
                product.getQuantity()
        ));

        return createdOrder;
    }

    public Order update(final Order order, final Long id) {
        order.setId(id);
        return this.repository.save(order);
    }

    public Response<Order> find(final PageRequest pageRequest) {
        Page<Order> foundServiceOrders = this.repository.findAll(PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize()));
        return Response
                .<Order>builder()
                .items(foundServiceOrders.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(foundServiceOrders.getTotalElements())
                .build();
    }

    public Order findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("service order not found"));
    }

    public void serviceOrderExists(final Long id) {
        this.findById(id);
    }

    public Order deleteById(final Long id) {
        final Order found = this.findById(id);
        found.getAttachments().forEach((photo) -> this.attachmentService.deleteById(photo.getId()));

        this.repository.delete(found);

        return found;
    }

    private void fetchProducts(final Order order) {
        order.getOrderProducts().forEach(orderProduct -> {
            final Product product = this.productService.findById(orderProduct.getProduct().getId());
            orderProduct.setProduct(product);
        });
    }

    private void fetchServices(final Order order) {
        order.getOrderServices().forEach(serviceOrderProduct -> {
            final com.softwarehouse.serviceorder.contexts.service.entities.Service service = this.serviceService.findById(serviceOrderProduct.getService().getId());
            serviceOrderProduct.setService(service);
        });
    }

    private void validateInventory(final Order order) {
        order.getOrderProducts().forEach(orderProduct -> {
            final int requestedQuantity = orderProduct.getQuantity();
            final Product product = orderProduct.getProduct();
            final InventoryValidation inventoryValidation = InventoryValidationUtils.validate(
                    requestedQuantity,
                    product
            );
            if (inventoryValidation.isUnavailable()) throw new InventoryValidationException(
                    "product not available: %s".formatted(product.getProductName()),
                    inventoryValidation
            );
        });
    }

    private void calculateServiceOrderProductSubtotal(final OrderProduct orderProductValue) {
        Optional.ofNullable(orderProductValue).ifPresent(orderProduct -> {
            final BigDecimal quantity = Optional.of(orderProduct.getQuantity())
                    .map(BigDecimal::valueOf)
                    .orElse(BigDecimal.ZERO);

            final BigDecimal price = Optional.of(orderProduct.getProduct())
                    .map(Product::getPrice)
                    .map(Price::getUsedSaleValue)
                    .orElse(BigDecimal.ZERO);

            final BigDecimal value = price.multiply(quantity);

            final BigDecimal valueWithDiscount = Optional.ofNullable(orderProduct.getDiscountAmount()) // 0 ou nulo
                    .map(discountAmount -> {
                        if (discountAmount.setScale(0, RoundingMode.CEILING).equals(BigDecimal.ZERO)) {
                            return Optional.of(orderProduct.getDiscountPercent()) // 0 ou nulo
                                    .map(value::multiply)
                                    .map(v -> v.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                                    .orElse(BigDecimal.ZERO);
                        }

                        return discountAmount;
                    })
                    .or(() -> Optional.ofNullable(orderProduct.getDiscountPercent()) // 0 ou nulo
                            .map(value::multiply)
                            .map(v -> v.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                            .or(() -> Optional.of(BigDecimal.ZERO)))
                    .map(value::subtract)
                    .orElse(BigDecimal.ZERO);

            orderProduct.setTotalValue(valueWithDiscount.setScale(2, RoundingMode.CEILING));
        });
    }

    private void calculateServiceOrderProductsSubtotal(final Order order) {
        order.getOrderProducts().forEach(this::calculateServiceOrderProductSubtotal);
    }

    private void calculateServiceOrderServiceSubtotal(final OrderService orderServiceValue) {
        Optional.ofNullable(orderServiceValue).ifPresent(orderService -> {
            final BigDecimal quantity = Optional.of(orderService.getQuantity())
                    .map(BigDecimal::valueOf)
                    .orElse(BigDecimal.ZERO);

            final BigDecimal price = Optional.ofNullable(orderService.getService())
                    .map(com.softwarehouse.serviceorder.contexts.service.entities.Service::getUnitCost)
                    .orElse(BigDecimal.ZERO);

            final BigDecimal value = price.multiply(quantity);

            final BigDecimal valueWithDiscount = Optional.ofNullable(orderService.getDiscountAmount())
                    .map(discountAmount -> {
                        if (discountAmount.setScale(0, RoundingMode.CEILING).equals(BigDecimal.ZERO)) {
                            return Optional.of(orderService.getDiscountPercent())
                                    .map(value::multiply)
                                    .map(v -> v.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                                    .orElse(BigDecimal.ZERO);
                        }

                        return discountAmount;
                    })
                    .or(() -> Optional.ofNullable(orderService.getDiscountPercent())
                            .map(value::multiply)
                            .map(v -> v.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                            .or(() -> Optional.of(BigDecimal.ZERO)))
                    .map(value::subtract)
                    .orElse(BigDecimal.ZERO);

            orderService.setTotalValue(valueWithDiscount.setScale(2, RoundingMode.CEILING));
        });
    }

    private void calculateServiceOrderServicesSubtotal(final Order order) {
        order.getOrderServices().forEach(this::calculateServiceOrderServiceSubtotal);
    }

    private void calculatePayments(final Order order) {
        Optional.ofNullable(order).ifPresent(so -> {
            final int installments = Optional
                    .ofNullable(so.getPayments())
                    .map(List::size)
                    .orElseThrow(() -> new BadRequestException("invalid installments size"));

            final BigDecimal installmentValue = order
                    .getTotal()
                    .divide(BigDecimal.valueOf(installments), RoundingMode.CEILING);

            so.getPayments().forEach(payment -> payment.getPayment().setValue(installmentValue));
        });
    }

    private void calculateTotals(final Order order) {
        this.calculateServiceOrderProductsSubtotal(order);
        this.calculateServiceOrderServicesSubtotal(order);

        final BigDecimal productsCostValue = order
                .getOrderProducts()
                .stream()
                .map(OrderProduct::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal servicesCostValue = order
                .getOrderServices()
                .stream()
                .map(OrderService::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal totalCostValue = productsCostValue.add(servicesCostValue);

        final BigDecimal totalValue = Optional.of(order.getDiscountAmount())
                .map(discountAmount -> {
                    if (discountAmount.setScale(0, RoundingMode.CEILING).equals(BigDecimal.ZERO)) {
                        return Optional.of(order.getDiscountPercent())
                                .map(totalCostValue::multiply)
                                .map(value -> value.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                                .orElse(BigDecimal.ZERO);
                    }

                    return discountAmount;
                })
                .or(() -> Optional.of(order.getDiscountPercent())
                        .map(totalCostValue::multiply)
                        .map(value -> value.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                        .or(() -> Optional.of(BigDecimal.ZERO)))
                .map(totalCostValue::subtract)
                .orElse(BigDecimal.ZERO);

        order.setTotal(totalValue.setScale(2, RoundingMode.CEILING));

        //this.calculatePayments(serviceOrder);
    }
}
