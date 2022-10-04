package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.model.AddressType;
import com.softwarehouse.serviceorder.service.AddressTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @ResponseStatus(HttpStatus.CREATED)
    public AddressType update(
            @PathVariable("id") final Long id,
            @RequestBody final AddressType addressType
    ) {
        addressType.setId(id);
        return this.service.save(addressType);
    }

    @GetMapping("/{id}")
    public AddressType findById(@PathVariable("id") final Long id) {
        return this.service.findById(id);
    }

    @DeleteMapping("/{id}")
    public AddressType deleteById(@PathVariable("id") final Long id) {
        return this.service.deleteById(id);
    }
}
