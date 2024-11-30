package com.example.l2g.config;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.regions.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Value("${aws.accessKeyId}")
    private String ACCESS_KEY;

    @Value("${aws.secretAccessKey}")
    private String SECRET_KEY;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        DynamoDbClient basicClient = DynamoDbClient.builder()
                .region(Region.US_EAST_2)  // Change this to your desired region
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .build();

        return basicClient;
    }
}
