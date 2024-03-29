package com.softwarehouse.serviceorder.contexts.service.controller;

import com.softwarehouse.serviceorder.contexts.service.entities.Service;
import com.softwarehouse.serviceorder.contexts.service.services.ServiceService;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Services")
@RequestMapping("/services")
public class ServiceController {
    private final ServiceService service;

    public ServiceController(final ServiceService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Service create(@RequestBody final Service service) {
        return this.service.create(service);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Service> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(PageRequest.of(page, size), filter);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Service findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Service update(@PathVariable("id") long id, @RequestBody final Service service) {
        this.service.serviceExists(id);
        return this.service.update(service, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Service delete(@PathVariable("id") long id) {
        return this.service.deleteById(id);
    }
}
