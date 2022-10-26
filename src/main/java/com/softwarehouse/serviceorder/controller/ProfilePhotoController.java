package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.exceptions.impl.InternalServerException;
import com.softwarehouse.serviceorder.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

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

    // TODO return Resource
    @GetMapping(value = "/{fileName}", headers = {"Content-Type=image/jpeg"})
    public byte[] download(@PathVariable("fileName") final String fileName) {
        try (var output = this.service.downloadFile(fileName)) {
            var baos = (ByteArrayOutputStream) output;
            return baos.toByteArray();
        } catch (Exception ex) {
            var errorMessage = "error trying to download file %s".formatted(fileName);
            log.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
    }
}
