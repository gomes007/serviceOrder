package com.softwarehouse.serviceorder.contexts.shared.repositories;

import com.softwarehouse.serviceorder.contexts.shared.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentsRepository extends JpaRepository<Attachment, Long> {
}
