package com.softwarehouse.serviceorder.contexts.shared.services;

import com.softwarehouse.serviceorder.contexts.shared.entities.ContactType;
import com.softwarehouse.serviceorder.contexts.shared.repositories.ContactTypeRepository;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactTypeService {
    private final ContactTypeRepository repository;

    public ContactTypeService(final ContactTypeRepository repository) {
        this.repository = repository;
    }

    public ContactType save(final ContactType contactType) {
        return this.repository.save(contactType);
    }

    public ContactType findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("contact type not found"));
    }

    public List<ContactType> findAll() {
        return this.repository.findAll();
    }

    public ContactType deleteById(final Long id) {
        final ContactType contactType = this.findById(id);

        this.repository.delete(contactType);

        return contactType;
    }
}
