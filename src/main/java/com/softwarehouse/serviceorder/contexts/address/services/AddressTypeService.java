package com.softwarehouse.serviceorder.contexts.address.services;

import com.softwarehouse.serviceorder.contexts.address.entities.AddressType;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.contexts.address.repositories.AddressTypeRepository;
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
                .orElseThrow(() -> new NotFoundException("address not found"));
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
