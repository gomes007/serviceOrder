package com.softwarehouse.serviceorder.contexts.shared.controllers;

import com.softwarehouse.serviceorder.contexts.shared.entities.Attachment;
import com.softwarehouse.serviceorder.contexts.shared.services.AttachmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Tag(name = "Attachments")
@RequestMapping("/attachments")
public class AttachmentsController {
    private final AttachmentService service;

    public AttachmentsController(final AttachmentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Attachment upload(@RequestParam("file") MultipartFile file) {
        return this.service.upload(file);
    }

    @DeleteMapping("/{attachmentId}")
    public Attachment delete(@PathVariable("attachmentId") final long attachmentId) {
        return this.service.deleteById(attachmentId);
    }
}
