package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.CostCenter;
import com.softwarehouse.serviceorder.domain.CostCenterStatus;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.service.CostCenterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cost-center")
public class CostCenterController {
    private final CostCenterService service;

    public CostCenterController(final CostCenterService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CostCenter register(@RequestBody final CostCenter costCenter) {
        return this.service.register(costCenter);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<CostCenter> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter,
            @RequestParam(name = "status", required = false) CostCenterStatus status
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(PageRequest.of(page, size), filter, status);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CostCenter findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CostCenter delete(@PathVariable("id") long id) {
        return this.service.disableById(id);
    }
}
