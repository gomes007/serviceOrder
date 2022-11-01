package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.Product;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(final ProductService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product register(@RequestBody final Product product) {
        return this.service.register(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Product> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable("id") long id, @RequestBody final Product product) {
        this.service.productExists(id);

        product.setId(id);

        return this.service.update(product, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product delete(@PathVariable("id") long id) {
        return this.service.deleteById(id);
    }
}
