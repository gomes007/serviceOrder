package com.softwarehouse.serviceorder.contexts.address.controllers;

import com.softwarehouse.serviceorder.contexts.address.entities.AddressType;
import com.softwarehouse.serviceorder.contexts.address.services.AddressTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Address Types")
@RequestMapping("/address/types")
public class AddressTypeController {
    private final AddressTypeService service;

    public AddressTypeController(final AddressTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<AddressType> findAll() {
        return this.service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressType create(@RequestBody final AddressType addressType) {
        return this.service.save(addressType);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressType update(
            @PathVariable("id") final Long id,
            @RequestBody final AddressType addressType
    ) {
        addressType.setId(id);
        return this.service.save(addressType);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressType findById(@PathVariable("id") final Long id) {
        return this.service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressType deleteById(@PathVariable("id") final Long id) {
        return this.service.deleteById(id);
    }
}
