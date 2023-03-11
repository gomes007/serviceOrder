package com.softwarehouse.serviceorder.contexts.service.services;

import com.softwarehouse.serviceorder.contexts.service.entities.Service;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import com.softwarehouse.serviceorder.contexts.service.repositories.ServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository repository;

    public ServiceService(final ServiceRepository repository) {
        this.repository = repository;
    }

    public Service create(final Service service) {
        return this.repository.save(service);
    }

    public void serviceExists(final Long id) {
        this.findById(id);
    }

    public Service update(final Service service, final Long id) {
        service.setId(id);
        return this.repository.save(service);
    }

    public Response<Service> find(final PageRequest pageRequest, final String filter) {
        Page<Service> services = this.repository.findAllByServiceNameIgnoreCaseContaining(
                filter,
                PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize())
        );
        return Response
                .<Service>builder()
                .items(services.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(services.getTotalElements())
                .build();
    }

    public Service findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("service not found"));
    }

    public Service deleteById(final Long id) {
        final Service found = this.findById(id);

        this.repository.delete(found);

        return found;
    }
}
