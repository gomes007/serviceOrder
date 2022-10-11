package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.PositionAndSalary;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.repository.PositionAndSalaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionAndSalaryService {
    private final PositionAndSalaryRepository repository;

    public PositionAndSalaryService(final PositionAndSalaryRepository repository) {
        this.repository = repository;
    }

    public PositionAndSalary save(final PositionAndSalary positionAndSalary) {
        return this.repository.save(positionAndSalary);
    }

    public PositionAndSalary findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("position not found"));
    }

    public List<PositionAndSalary> findAll() {
        return this.repository.findAll();
    }

    public PositionAndSalary deleteById(final Long id) {
        final PositionAndSalary addressType = this.findById(id);

        this.repository.delete(addressType);

        return addressType;
    }
}
