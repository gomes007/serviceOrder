package com.softwarehouse.serviceorder.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class FileUploadServiceIT {
    @Autowired
    private FileUploadService service;

    private static String readFileContent(final InputStream inputStream) {
        return new String(((ByteArrayInputStream) inputStream).readAllBytes(), StandardCharsets.UTF_8);
    }

    @Test
    void shouldUploadFile() {
        // given:
        var content = "Test";
        var multipartFile = new MockMultipartFile(
                "test.txt",
                content.getBytes(StandardCharsets.UTF_8)
        );

        // when:
        String fileName = this.service.uploadFile(multipartFile);
        var file = this.service.downloadFile(fileName);
        var fileContent = readFileContent(file);
        this.service.deleteFile(fileName);

        // then:
        assertEquals(content, fileContent);
    }
}