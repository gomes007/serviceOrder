package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.Attachment;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.repository.AttachmentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class AttachmentService {
    private final AttachmentsRepository repository;
    private final FileUploadService fileUploadService;

    public AttachmentService(
            final AttachmentsRepository repository,
            final FileUploadService fileUploadService
    ) {
        this.repository = repository;
        this.fileUploadService = fileUploadService;
    }

    public Attachment upload(final MultipartFile multipartFile) {
        var location = this.fileUploadService.uploadFile(multipartFile);

        var attachment = new Attachment();
        attachment.setLocation(location);
        attachment.setName(multipartFile.getOriginalFilename());

        return this.repository.save(attachment);
    }

    public Attachment findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("attachment not found"));
    }

    public Attachment deleteById(final Long id) {
        final Attachment attachment = this.findById(id);

        this.fileUploadService.deleteFile(attachment.getLocation());

        this.repository.delete(attachment);

        return attachment;
    }
}
