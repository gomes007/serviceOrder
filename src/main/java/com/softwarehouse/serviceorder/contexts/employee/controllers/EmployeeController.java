package com.softwarehouse.serviceorder.contexts.employee.controllers;

import com.softwarehouse.serviceorder.contexts.employee.entities.Employee;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import com.softwarehouse.serviceorder.contexts.employee.services.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Employees")
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(final EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee register(@RequestBody final Employee employee) {
        return this.service.save(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Employee> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter,
            @RequestParam(name = "position", defaultValue = "") String position,
            @RequestParam(name = "role", defaultValue = "") String role
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(filter, position, role, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee update(@PathVariable("id") long id, @RequestBody final Employee employee) {
        this.service.employeeExists(id);

        employee.setId(id);

        return this.service.save(employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee delete(@PathVariable("id") long id) {
        return this.service.deleteById(id);
    }
}
