package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.Attachment;
import com.softwarehouse.serviceorder.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
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
