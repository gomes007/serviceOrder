package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/photo")
public class ProfilePhotoController {
    private final FileUploadService service;

    public ProfilePhotoController(final FileUploadService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadPhoto(@RequestParam("file") MultipartFile file) {
        return this.service.uploadFile(file);
    }

    @GetMapping("/{fileName}")
    public Resource download(@PathVariable("fileName") final String fileName) {
        return new InputStreamResource(this.service.downloadFile(fileName));
    }
}
