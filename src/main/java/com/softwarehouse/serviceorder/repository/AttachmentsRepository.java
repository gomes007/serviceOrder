package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentsRepository extends JpaRepository<Attachment, Long> {
}
