package com.softwarehouse.serviceorder.contexts.shared.repositories;

import com.softwarehouse.serviceorder.contexts.shared.entities.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {
}
