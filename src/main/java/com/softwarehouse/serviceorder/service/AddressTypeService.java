package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.model.AddressType;
import com.softwarehouse.serviceorder.repository.AddressTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressTypeService {
    private final AddressTypeRepository repository;

    public AddressTypeService(final AddressTypeRepository repository) {
        this.repository = repository;
    }

    public AddressType save(final AddressType addressType) {
        return this.repository.save(addressType);
    }

    public AddressType findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("address not found"));
    }

    public List<AddressType> findAll() {
        return this.repository.findAll();
    }

    public AddressType deleteById(final Long id) {
        final AddressType addressType = this.findById(id);

        this.repository.delete(addressType);

        return addressType;
    }
}
