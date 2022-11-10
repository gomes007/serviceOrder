package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.*;
import com.softwarehouse.serviceorder.exceptions.impl.BadRequestException;
import com.softwarehouse.serviceorder.exceptions.impl.InventoryValidationException;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.model.InventoryValidation;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.repository.ServiceOrderRepository;
import com.softwarehouse.serviceorder.utils.InventoryValidationUtils;
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
public class ServiceOrderService {
    private final ServiceOrderRepository repository;
    private final AttachmentService attachmentService;
    private final InventoryService inventoryService;
    private final ProductService productService;
    private final ServiceService serviceService;

    public ServiceOrderService(
            final ServiceOrderRepository repository,
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

    public ServiceOrder open(final ServiceOrder serviceOrder) {
        this.fetchProducts(serviceOrder);
        this.fetchServices(serviceOrder);
        this.validateInventory(serviceOrder);
        this.calculateTotals(serviceOrder);

        serviceOrder.setStatus(ServiceOrderStatus.OPEN);

        // TODO criar conta a receber (AccountsReceivableService) para cada pagamento
        ServiceOrder updatedOrder = this.repository.save(serviceOrder);
        serviceOrder.getServiceOrderProducts().forEach(product -> this.inventoryService.subtractAmount(
                product.getProduct().getId(),
                product.getQuantity()
        ));
        return updatedOrder;
    }

    public ServiceOrder update(final ServiceOrder serviceOrder, final Long id) {
        serviceOrder.setId(id);
        return this.repository.save(serviceOrder);
    }

    public Response<ServiceOrder> find(final PageRequest pageRequest) {
        Page<ServiceOrder> foundServiceOrders = this.repository.findAll(PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize()));
        return Response
                .<ServiceOrder>builder()
                .items(foundServiceOrders.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(foundServiceOrders.getTotalElements())
                .build();
    }

    public ServiceOrder findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("service order not found"));
    }

    public void serviceOrderExists(final Long id) {
        this.findById(id);
    }

    public ServiceOrder deleteById(final Long id) {
        final ServiceOrder found = this.findById(id);
        found.getAttachments().forEach((photo) -> this.attachmentService.deleteById(photo.getId()));

        this.repository.delete(found);

        return found;
    }

    private void fetchProducts(final ServiceOrder serviceOrder) {
        serviceOrder.getServiceOrderProducts().forEach(serviceOrderProduct -> {
            final Product product = this.productService.findById(serviceOrderProduct.getProduct().getId());
            serviceOrderProduct.setProduct(product);
        });
    }

    private void fetchServices(final ServiceOrder serviceOrder) {
        serviceOrder.getServiceOrderServices().forEach(serviceOrderProduct -> {
            final com.softwarehouse.serviceorder.domain.Service service = this.serviceService.findById(serviceOrderProduct.getService().getId());
            serviceOrderProduct.setService(service);
        });
    }

    private void validateInventory(final ServiceOrder serviceOrder) {
        serviceOrder.getServiceOrderProducts().forEach(serviceOrderProduct -> {
            final int requestedQuantity = serviceOrderProduct.getQuantity();
            final Product product = serviceOrderProduct.getProduct();
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

    private void calculateServiceOrderProductSubtotal(final ServiceOrderProduct serviceOrderProductValue) {
        Optional.ofNullable(serviceOrderProductValue).ifPresent(serviceOrderProduct -> {
            final BigDecimal quantity = Optional.of(serviceOrderProduct.getQuantity())
                    .map(BigDecimal::valueOf)
                    .orElse(BigDecimal.ZERO);

            final BigDecimal price = Optional.of(serviceOrderProduct.getProduct())
                    .map(Product::getPrice)
                    .map(Price::getUsedSaleValue)
                    .orElse(BigDecimal.ZERO);

            final BigDecimal value = price.multiply(quantity);

            final BigDecimal valueWithDiscount = Optional.of(serviceOrderProduct.getDiscountAmount()) // 0 ou nulo
                    .map(discountAmount -> {
                        if (discountAmount.setScale(0, RoundingMode.CEILING).equals(BigDecimal.ZERO)) {
                            return Optional.of(serviceOrderProduct.getDiscountPercent()) // 0 ou nulo
                                    .map(value::multiply)
                                    .map(v -> v.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                                    .orElse(BigDecimal.ZERO);
                        }

                        return discountAmount;
                    })
                    .or(() -> Optional.of(serviceOrderProduct.getDiscountPercent()) // 0 ou nulo
                            .map(value::multiply)
                            .map(v -> v.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                            .or(() -> Optional.of(BigDecimal.ZERO)))
                    .map(value::subtract)
                    .orElse(BigDecimal.ZERO);

            serviceOrderProduct.setTotalValue(valueWithDiscount.setScale(2, RoundingMode.CEILING));
        });
    }

    private void calculateServiceOrderProductsSubtotal(final ServiceOrder serviceOrder) {
        serviceOrder.getServiceOrderProducts().forEach(this::calculateServiceOrderProductSubtotal);
    }

    private void calculateServiceOrderServiceSubtotal(final com.softwarehouse.serviceorder.domain.ServiceOrderService serviceOrderServiceValue) {
        Optional.ofNullable(serviceOrderServiceValue).ifPresent(serviceOrderService -> {
            final BigDecimal quantity = Optional.of(serviceOrderService.getQuantity())
                    .map(BigDecimal::valueOf)
                    .orElse(BigDecimal.ZERO);

            final BigDecimal price = Optional.of(serviceOrderService.getService())
                    .map(com.softwarehouse.serviceorder.domain.Service::getUnitCost)
                    .orElse(BigDecimal.ZERO);

            final BigDecimal value = price.multiply(quantity);

            final BigDecimal valueWithDiscount = Optional.of(serviceOrderService.getDiscountAmount())
                    .map(discountAmount -> {
                        if (discountAmount.setScale(0, RoundingMode.CEILING).equals(BigDecimal.ZERO)) {
                            return Optional.of(serviceOrderService.getDiscountPercent())
                                    .map(value::multiply)
                                    .map(v -> v.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                                    .orElse(BigDecimal.ZERO);
                        }

                        return discountAmount;
                    })
                    .or(() -> Optional.of(serviceOrderService.getDiscountPercent())
                            .map(value::multiply)
                            .map(v -> v.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                            .or(() -> Optional.of(BigDecimal.ZERO)))
                    .map(value::subtract)
                    .orElse(BigDecimal.ZERO);

            serviceOrderService.setTotalValue(valueWithDiscount.setScale(2, RoundingMode.CEILING));
        });
    }

    private void calculateServiceOrderServicesSubtotal(final ServiceOrder serviceOrder) {
        serviceOrder.getServiceOrderServices().forEach(this::calculateServiceOrderServiceSubtotal);
    }

    private void calculatePayments(final ServiceOrder serviceOrder) {
        Optional.ofNullable(serviceOrder).ifPresent(so -> {
            final int installments = Optional
                    .ofNullable(so.getPayments())
                    .map(List::size)
                    .orElseThrow(() -> new BadRequestException("invalid installments size"));

            final BigDecimal installmentValue = serviceOrder
                    .getTotal()
                    .divide(BigDecimal.valueOf(installments), RoundingMode.CEILING);

            so.getPayments().forEach(payment -> payment.setValue(installmentValue));
        });
    }

    private void calculateTotals(final ServiceOrder serviceOrder) {
        this.calculateServiceOrderProductsSubtotal(serviceOrder);
        this.calculateServiceOrderServicesSubtotal(serviceOrder);

        final BigDecimal productsCostValue = serviceOrder
                .getServiceOrderProducts()
                .stream()
                .map(ServiceOrderProduct::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal servicesCostValue = serviceOrder
                .getServiceOrderServices()
                .stream()
                .map(com.softwarehouse.serviceorder.domain.ServiceOrderService::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal totalCostValue = productsCostValue.add(servicesCostValue);

        final BigDecimal totalValue = Optional.of(serviceOrder.getDiscountAmount())
                .map(discountAmount -> {
                    if (discountAmount.setScale(0, RoundingMode.CEILING).equals(BigDecimal.ZERO)) {
                        return Optional.of(serviceOrder.getDiscountPercent())
                                .map(totalCostValue::multiply)
                                .map(value -> value.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                                .orElse(BigDecimal.ZERO);
                    }

                    return discountAmount;
                })
                .or(() -> Optional.of(serviceOrder.getDiscountPercent())
                        .map(totalCostValue::multiply)
                        .map(value -> value.divide(BigDecimal.valueOf(100.00), RoundingMode.CEILING))
                        .or(() -> Optional.of(BigDecimal.ZERO)))
                .map(totalCostValue::subtract)
                .orElse(BigDecimal.ZERO);

        serviceOrder.setTotal(totalValue.setScale(2, RoundingMode.CEILING));

        //this.calculatePayments(serviceOrder);
    }
}
