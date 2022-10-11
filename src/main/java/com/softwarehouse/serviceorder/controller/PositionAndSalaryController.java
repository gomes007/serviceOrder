package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.AddressType;
import com.softwarehouse.serviceorder.domain.PositionAndSalary;
import com.softwarehouse.serviceorder.service.AddressTypeService;
import com.softwarehouse.serviceorder.service.PositionAndSalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionAndSalaryController {
    private final PositionAndSalaryService service;

    public PositionAndSalaryController(final PositionAndSalaryService service) {
        this.service = service;
    }

    @GetMapping
    public List<PositionAndSalary> findAll() {
        return this.service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PositionAndSalary create(@RequestBody final PositionAndSalary positionAndSalary) {
        return this.service.save(positionAndSalary);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionAndSalary update(
            @PathVariable("id") final Long id,
            @RequestBody final PositionAndSalary positionAndSalary
    ) {
        positionAndSalary.setId(id);
        return this.service.save(positionAndSalary);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionAndSalary findById(@PathVariable("id") final Long id) {
        return this.service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionAndSalary deleteById(@PathVariable("id") final Long id) {
        return this.service.deleteById(id);
    }
}
