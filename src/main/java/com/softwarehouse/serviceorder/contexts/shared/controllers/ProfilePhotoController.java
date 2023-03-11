package com.softwarehouse.serviceorder.contexts.shared.controllers;

import com.softwarehouse.serviceorder.contexts.shared.services.FileUploadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/photo")
@Tag(name = "Profile Photo Uploader")
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
}
