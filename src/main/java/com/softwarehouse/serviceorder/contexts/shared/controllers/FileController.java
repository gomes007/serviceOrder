package com.softwarehouse.serviceorder.contexts.shared.controllers;

import com.softwarehouse.serviceorder.contexts.shared.services.FileUploadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/file")
@Tag(name = "File Upload and Download")
public class FileController {
    private final FileUploadService service;

    public FileController(final FileUploadService service) {
        this.service = service;
    }

    @GetMapping("/{fileName}")
    public Resource download(@PathVariable("fileName") final String fileName) {
        return new InputStreamResource(this.service.downloadFile(fileName));
    }
}
