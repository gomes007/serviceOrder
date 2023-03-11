package com.softwarehouse.serviceorder.contexts.employee.controllers;

import com.softwarehouse.serviceorder.contexts.employee.entities.PositionSalary;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import com.softwarehouse.serviceorder.contexts.employee.services.PositionSalaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Positions and Salaries")
@RequestMapping("/positions")
public class PositionSalaryController {
    private final PositionSalaryService service;

    public PositionSalaryController(final PositionSalaryService service) {
        this.service = service;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Response<PositionSalary> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter,
            @RequestParam(name = "role", defaultValue = "") String role
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.findAll(filter, role, PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PositionSalary create(@RequestBody final PositionSalary positionSalary) {
        return this.service.save(positionSalary);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionSalary update(
            @PathVariable("id") final Long id,
            @RequestBody final PositionSalary positionSalary
    ) {
        positionSalary.setId(id);
        return this.service.save(positionSalary);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionSalary findById(@PathVariable("id") final Long id) {
        return this.service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionSalary deleteById(@PathVariable("id") final Long id) {
        return this.service.deleteById(id);
    }
}
