package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.CostCenter;
import com.softwarehouse.serviceorder.domain.CostCenterStatus;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.repository.CostCenterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CostCenterService {
    private final CostCenterRepository repository;

    public CostCenterService(final CostCenterRepository repository) {
        this.repository = repository;
    }

    public CostCenter register(final CostCenter costCenter) {
        costCenter.setStatus(CostCenterStatus.ENABLED);
        return this.repository.save(costCenter);
    }

    public Response<CostCenter> find(final PageRequest pageRequest, final String filter, final CostCenterStatus status) {
        Page<CostCenter> customers = Optional
                .ofNullable((status))
                .map(gotStatus -> this.repository.findAllByNameIgnoreCaseContainingAndStatus(
                        filter,
                        status,
                        PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize())
                ))
                .orElseGet(() -> this.repository.findAllByNameIgnoreCaseContaining(
                        filter,
                        PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize())
                ));
        return Response
                .<CostCenter>builder()
                .items(customers.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(customers.getTotalElements())
                .build();
    }

    public CostCenter findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("cost center not found"));
    }

    public CostCenter disableById(final Long id) {
        final CostCenter found = this.findById(id);
        found.setStatus(CostCenterStatus.DISABLED);

        this.repository.save(found);

        return found;
    }
}
