package com.softwarehouse.serviceorder.contexts.shared.controllers;

import com.softwarehouse.serviceorder.contexts.shared.entities.ContactType;
import com.softwarehouse.serviceorder.contexts.shared.services.ContactTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Contact Types")
@RequestMapping("/contacts/types")
public class ContactTypeController {
    private final ContactTypeService service;

    public ContactTypeController(final ContactTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ContactType> findAll() {
        return this.service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactType create(@RequestBody final ContactType contactType) {
        return this.service.save(contactType);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactType update(
            @PathVariable("id") final Long id,
            @RequestBody final ContactType contactType
    ) {
        contactType.setId(id);
        return this.service.save(contactType);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactType findById(@PathVariable("id") final Long id) {
        return this.service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactType deleteById(@PathVariable("id") final Long id) {
        return this.service.deleteById(id);
    }
}
