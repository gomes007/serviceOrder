package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.service.FileUploadService;
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
