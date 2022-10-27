package com.softwarehouse.serviceorder.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.softwarehouse.serviceorder.exceptions.impl.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

@Slf4j
@Service
public class FileUploadService {
    private final AmazonS3 amazonS3;
    private final String bucketName;

    public FileUploadService(
            final AmazonS3 amazonS3,
            @Value("${aws.s3.fileUpload.bucket}") final String bucketName
    ) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public String uploadFile(final MultipartFile multipartFile) {
        final String fileType = Optional.ofNullable(multipartFile.getContentType())
                .map(contentType -> contentType.split("/"))
                .map(splittedContentType -> {
                    var type = splittedContentType[0];
                    var content = splittedContentType[1];

                    if (type.equals("image") || type.equals("application")) {
                        return "." + content;
                    }
                    return ".file";
                })
                .orElse(".file");

        final String fileName = System.currentTimeMillis() + fileType;

        try {
            log.info("trying to upload file {} to bucket {}", fileName, this.bucketName);
            amazonS3.putObject(
                    this.bucketName,
                    fileName,
                    multipartFile.getInputStream(),
                    new ObjectMetadata()
            );

            return fileName;
        } catch (Exception ex) {
            log.error("failed to upload file {} to bucket {}", fileName, this.bucketName, ex);
            throw new InternalServerException(ex.getMessage());
        }

    }

    public InputStream downloadFile(final String fileName) {
        try {
            log.info("trying to download file {} from bucket {}", fileName, this.bucketName);
            var content = amazonS3.getObject(
                    this.bucketName,
                    fileName
            );

            return new ByteArrayInputStream(content.getObjectContent().readAllBytes());
        } catch (Exception ex) {
            log.error("failed to download file {} from bucket {}", fileName, this.bucketName, ex);
            throw new InternalServerException(ex.getMessage());
        }
    }

    public void deleteFile(final String fileName) {
        try {
            log.info("trying to delete file {} from bucket {}", fileName, this.bucketName);
            amazonS3.deleteObject(this.bucketName, fileName);
        } catch (Exception ex) {
            log.error("failed to delete file {} from bucket {}", fileName, this.bucketName, ex);
            throw new InternalServerException(ex.getMessage());
        }
    }
}
