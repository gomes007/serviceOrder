package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.Price;
import com.softwarehouse.serviceorder.domain.Product;
import com.softwarehouse.serviceorder.domain.ServiceOrder;
import com.softwarehouse.serviceorder.domain.ServiceOrderProduct;
import com.softwarehouse.serviceorder.domain.ServiceOrderStatus;
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
import java.util.Optional;

@Slf4j
@Service
public class ServiceOrderService {
    private final ServiceOrderRepository repository;
    private final AttachmentService attachmentService;

    public ServiceOrderService(
            final ServiceOrderRepository repository,
            final AttachmentService attachmentService
    ) {
        this.repository = repository;
        this.attachmentService = attachmentService;
    }

    public ServiceOrder open(final ServiceOrder serviceOrder) {
        this.validateInventory(serviceOrder);
        this.calculateTotals(serviceOrder);

        serviceOrder.setStatus(ServiceOrderStatus.OPEN);

        return serviceOrder;
//        return this.repository.save(serviceOrder);
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

            final BigDecimal valueWithDiscount = Optional.of(serviceOrderProduct.getDiscountAmount())
                    .map(discountAmount -> {
                        if (discountAmount.equals(BigDecimal.ZERO)) {
                            return Optional.of(serviceOrderProduct.getDiscountPercent())
                                    .map(value::multiply)
                                    .orElse(BigDecimal.ZERO);
                        }

                        return discountAmount;
                    })
                    .or(() -> Optional.of(serviceOrderProduct.getDiscountPercent())
                            .map(value::multiply)
                            .or(() -> Optional.of(BigDecimal.ZERO)))
                    .map(value::subtract)
                    .orElse(BigDecimal.ZERO);

            serviceOrderProduct.setTotalValue(valueWithDiscount);
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
                        if (discountAmount.equals(BigDecimal.ZERO)) {
                            return Optional.of(serviceOrderService.getDiscountPercent())
                                    .map(value::multiply)
                                    .orElse(BigDecimal.ZERO);
                        }

                        return discountAmount;
                    })
                    .or(() -> Optional.of(serviceOrderService.getDiscountPercent())
                            .map(value::multiply)
                            .or(() -> Optional.of(BigDecimal.ZERO)))
                    .map(value::subtract)
                    .orElse(BigDecimal.ZERO);

            serviceOrderService.setTotalValue(valueWithDiscount);
        });
    }

    private void calculateServiceOrderServicesSubtotal(final ServiceOrder serviceOrder) {
        serviceOrder.getServiceOrderServices().forEach(this::calculateServiceOrderServiceSubtotal);
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
                    if (discountAmount.equals(BigDecimal.ZERO)) {
                        return Optional.of(serviceOrder.getDiscountPercent())
                                .map(totalCostValue::multiply)
                                .orElse(BigDecimal.ZERO);
                    }

                    return discountAmount;
                })
                .or(() -> Optional.of(serviceOrder.getDiscountPercent())
                        .map(totalCostValue::multiply)
                        .or(() -> Optional.of(BigDecimal.ZERO)))
                .map(totalCostValue::subtract)
                .orElse(BigDecimal.ZERO);

        serviceOrder.setTotal(totalValue);
    }
}
