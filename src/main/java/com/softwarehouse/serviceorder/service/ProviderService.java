package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.Customer;
import com.softwarehouse.serviceorder.domain.Provider;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.repository.CustomerRepository;
import com.softwarehouse.serviceorder.repository.ProviderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    private final ProviderRepository repository;

    public ProviderService(final ProviderRepository repository) {
        this.repository = repository;
    }

    public Provider save(final Provider provider) {
        return this.repository.save(provider);
    }

    public Response<Provider> find(final PageRequest pageRequest) {
        Page<Provider> providers = this.repository.findAll(PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize()));
        return Response
                .<Provider>builder()
                .items(providers.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(providers.getTotalElements())
                .build();
    }

    public Provider findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("provider not found"));
    }

    public void providerExists(final Long id) {
        this.findById(id);
    }

    public Provider deleteById(final Long id) {
        final Provider found = this.findById(id);

        this.repository.delete(found);

        return found;
    }
}
