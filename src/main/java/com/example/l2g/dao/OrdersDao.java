package com.example.l2g.dao;

import com.example.l2g.model.sending.StockedProduct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class OrdersDao {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName = "Products";
    private final String partitionKey = "ProductId";

    @Value("${awsS3.baseurl}")
    private String S3_BASE_URL;

    public OrdersDao(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

//    public void writeUserOrder(long uid, )



}
