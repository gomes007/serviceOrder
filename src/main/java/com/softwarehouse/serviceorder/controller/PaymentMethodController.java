package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.PaymentMethod;
import com.softwarehouse.serviceorder.domain.PaymentMethodAvailability;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.service.PaymentMethodService;
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

@RestController
@RequestMapping("/payments/methods")
public class PaymentMethodController {
    private final PaymentMethodService service;

    public PaymentMethodController(final PaymentMethodService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethod create(@RequestBody final PaymentMethod paymentMethod) {
        return this.service.create(paymentMethod);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<PaymentMethod> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter,
            @RequestParam(name = "availability", defaultValue = "BOTH") PaymentMethodAvailability availability
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(PageRequest.of(page, size), filter, availability);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentMethod findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentMethod update(@PathVariable("id") long id, @RequestBody final PaymentMethod paymentMethod) {
        this.service.paymentMethodExists(id);
        return this.service.update(paymentMethod, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentMethod delete(@PathVariable("id") long id) {
        return this.service.deleteById(id);
    }
}
