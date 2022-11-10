package com.softwarehouse.serviceorder.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    @Bean("credentialsProvider")
    public AWSCredentialsProvider provider(
            @Value("${aws.key}") final String key,
            @Value("${aws.secret}") final String secret
    ) {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(key, secret));
    }

    @Bean
    public AmazonS3 amazonS3(
            final AWSCredentialsProvider credentialsProvider,
            @Value("${aws.s3.region}") final String region
    ) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion(region)
                .build();
    }
}
