package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.PositionSalary;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.repository.PositionSalaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PositionSalaryService {
    private final PositionSalaryRepository repository;

    public PositionSalaryService(final PositionSalaryRepository repository) {
        this.repository = repository;
    }

    public PositionSalary save(final PositionSalary positionSalary) {
        return this.repository.save(positionSalary);
    }

    public PositionSalary findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("position not found"));
    }

    public Response<PositionSalary> findAll(final String filter, final String role, final PageRequest pageRequest) {
        var page = this.repository.findAllByPositionIgnoreCaseContainingAndRoleIgnoreCaseContaining(
                filter,
                role,
                PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize())
        );
        return Response
                .<PositionSalary>builder()
                .items(page.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(page.getTotalElements())
                .build();
    }

    public PositionSalary deleteById(final Long id) {
        final PositionSalary addressType = this.findById(id);

        this.repository.delete(addressType);

        return addressType;
    }
}
