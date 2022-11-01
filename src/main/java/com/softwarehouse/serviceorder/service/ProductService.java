package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.Product;
import com.softwarehouse.serviceorder.exceptions.impl.BadRequestException;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(final ProductRepository repository) {
        this.repository = repository;
    }

    public Product register(final Product product) {
        return this.repository.save(product);
    }

    public Product update(final Product product, final Long id) {
        if (product.getInventory() == null || product.getInventory().getId() == null)
            throw new BadRequestException("product has invalid inventory id");
        var savedProduct = this.findById(id);

        product.setId(id);
        product.setInventory(Optional
                .ofNullable(product.getInventory())
                .map(inventory -> {
                    inventory.setCurrentQuantity(savedProduct.getInventory().getCurrentQuantity());
                    return inventory;
                })
                .orElse(savedProduct.getInventory()));

        return this.repository.save(product);
    }

    public Response<Product> find(final PageRequest pageRequest) {
        Page<Product> products = this.repository.findAll(PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize()));
        return Response
                .<Product>builder()
                .items(products.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(products.getTotalElements())
                .build();
    }

    public Product findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("product not found"));
    }

    public void productExists(final Long id) {
        this.findById(id);
    }

    public Product deleteById(final Long id) {
        final Product found = this.findById(id);

        this.repository.delete(found);

        return found;
    }
}
