package com.softwarehouse.serviceorder.contexts.provider.controllers;

import com.softwarehouse.serviceorder.contexts.provider.entities.Provider;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import com.softwarehouse.serviceorder.contexts.provider.services.ProviderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Providers")
@RequestMapping("/providers")
public class ProviderController {
    private final ProviderService service;

    public ProviderController(final ProviderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Provider register(@RequestBody final Provider provider) {
        return this.service.save(provider);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Provider> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Provider findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Provider update(@PathVariable("id") long id, @RequestBody final Provider provider) {
        this.service.providerExists(id);

        provider.setId(id);

        return this.service.save(provider);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Provider delete(@PathVariable("id") long id) {
        return this.service.deleteById(id);
    }
}
