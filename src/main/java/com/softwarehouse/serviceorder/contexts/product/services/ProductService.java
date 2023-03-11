package com.softwarehouse.serviceorder.contexts.product.services;

import com.softwarehouse.serviceorder.contexts.product.entities.Inventory;
import com.softwarehouse.serviceorder.contexts.product.entities.Product;
import com.softwarehouse.serviceorder.exceptions.impl.BadRequestException;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import com.softwarehouse.serviceorder.contexts.product.repositories.ProductRepository;
import com.softwarehouse.serviceorder.contexts.shared.services.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository repository;
    private final AttachmentService attachmentService;

    public ProductService(
            final ProductRepository repository,
            final AttachmentService attachmentService
    ) {
        this.repository = repository;
        this.attachmentService = attachmentService;
    }

    public Product register(final Product product) {
        return this.repository.save(product);
    }

    public Product update(final Product product, final Long id) {
        Optional
                .ofNullable(product.getInventory())
                .map(Inventory::getId)
                .orElseThrow(() -> new BadRequestException("product has invalid inventory id"));

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
        found.getPhotos().forEach((photo) -> this.attachmentService.deleteById(photo.getId()));

        this.repository.delete(found);

        return found;
    }
}
