package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.ServiceOrder;
import com.softwarehouse.serviceorder.domain.ServiceOrderStatus;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.service.ServiceOrderService;
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
@RequestMapping("/service-order")
public class ServiceOrderController {
    private final ServiceOrderService service;

    public ServiceOrderController(final ServiceOrderService service) {
        this.service = service;
    }

    @GetMapping("/possible-status")
    public Map<ServiceOrderStatus, String> getPossibleStatus() {
        var statusAndLabel = new HashMap<ServiceOrderStatus, String>();

        for (var value : ServiceOrderStatus.values()) {
            statusAndLabel.put(value, value.getLabel());
        }

        return statusAndLabel;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOrder open(@RequestBody final ServiceOrder serviceOrder) {
        return this.service.open(serviceOrder);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<ServiceOrder> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceOrder findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceOrder update(@PathVariable("id") long id, @RequestBody final ServiceOrder serviceOrder) {
        this.service.serviceOrderExists(id);
        return this.service.update(serviceOrder, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceOrder delete(@PathVariable("id") long id) {
        return this.service.deleteById(id);
    }
}
