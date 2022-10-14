package com.softwarehouse.serviceorder.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.softwarehouse.serviceorder.exceptions.impl.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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
        try {
            final String fileName = System.currentTimeMillis() + ".file";
            amazonS3.putObject(
                    this.bucketName,
                    fileName,
                    multipartFile.getInputStream(),
                    new ObjectMetadata()
            );

            return fileName;
        } catch (Exception ex) {
            throw new InternalServerException(ex.getMessage());
        }
    }

    public OutputStream downloadFile(final String fileName) {
        try {
            var content = amazonS3.getObjectAsString(
                    this.bucketName,
                    fileName
            );

            return writeFile(content);
        } catch (Exception ex) {
            throw new InternalServerException(ex.getMessage());
        }
    }

    public void deleteFile(final String fileName) {
        try {
            amazonS3.deleteObject(this.bucketName, fileName);
        } catch (Exception ex) {
            throw new InternalServerException(ex.getMessage());
        }
    }

    private static OutputStream writeFile(final String content) {
        var data = new ByteArrayOutputStream();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(data))) {
            writer.write(content);
            return data;
        } catch (Exception ex) {
            throw new InternalServerException(ex.getMessage());
        }
    }
}
