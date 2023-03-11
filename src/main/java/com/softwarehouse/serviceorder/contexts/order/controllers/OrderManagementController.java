package com.softwarehouse.serviceorder.contexts.order.controllers;

import com.softwarehouse.serviceorder.contexts.order.entities.Order;
import com.softwarehouse.serviceorder.contexts.order.models.OrderStatus;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import com.softwarehouse.serviceorder.contexts.order.services.OrderManagementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Orders Management")
@RequestMapping("/orders")
public class OrderManagementController {
    private final OrderManagementService service;

    public OrderManagementController(final OrderManagementService service) {
        this.service = service;
    }

    @GetMapping("/possible-status")
    public Map<OrderStatus, String> getPossibleStatus() {
        var statusAndLabel = new HashMap<OrderStatus, String>();

        for (var value : OrderStatus.values()) {
            statusAndLabel.put(value, value.getLabel());
        }

        return statusAndLabel;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order open(@RequestBody final Order order) {
        return this.service.open(order);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Order> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order update(@PathVariable("id") long id, @RequestBody final Order order) {
        this.service.serviceOrderExists(id);
        return this.service.update(order, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order delete(@PathVariable("id") long id) {
        return this.service.deleteById(id);
    }
}
