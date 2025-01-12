package com.example.l2g.dao;

import com.example.l2g.model.sending.OrderToFulfill;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDataDao {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName = "UserData";

    @Value("${awsS3.baseurl}")
    private String S3_BASE_URL;

    public UserDataDao(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void createUserEntry(String uid) {
        // Create the item map with "uid" as the field name and the parameter as the value
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("uid", AttributeValue.builder().s(uid).build());

        // Build the PutItemRequest
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        // Write the item to DynamoDB
        dynamoDbClient.putItem(putItemRequest);
    }

    public boolean checkUserExists(String uid) {
        // Build the key to query the table
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("uid", AttributeValue.builder().s(uid).build());

        // Build the GetItemRequest
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();

        // Query DynamoDB
        GetItemResponse response = dynamoDbClient.getItem(getItemRequest);

        // Check if the item exists
        return response.hasItem();
    }

    public void addOrderToUser(String orderId, String uid) {
        // Create the key to identify the user
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("uid", AttributeValue.builder().s(uid).build());

        // Define the update expression
        String updateExpression = "SET orders = list_append(if_not_exists(orders, :emptyList), :newOrder)";

        // Define the expression attribute values
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":newOrder", AttributeValue.builder().l(
                AttributeValue.builder().s(orderId).build()
        ).build());

        // Create an explicitly empty list
        expressionAttributeValues.put(":emptyList", AttributeValue.builder().l(new ArrayList<>()).build());

        // Build the update request
        UpdateItemRequest updateRequest = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .updateExpression(updateExpression)
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        // Execute the update
        dynamoDbClient.updateItem(updateRequest);
    }




}