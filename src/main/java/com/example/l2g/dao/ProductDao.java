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
public class ProductDao {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName = "Products";
    private final String partitionKey = "ProductId";

    @Value("${awsS3.baseurl}")
    private String S3_BASE_URL;


    public ProductDao(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public List<StockedProduct> getAllProducts() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

        List<StockedProduct> list = scanResponse.items().stream()
                .map(this::mapToStockedProduct)
                .collect(Collectors.toList());

        System.out.println(list);

        return list;

    }


    public StockedProduct getProductById(String id) {

        HashMap<String,AttributeValue> key_to_get =
                new HashMap<String, AttributeValue>();

        key_to_get.put(partitionKey, AttributeValue.builder()
                .n(id)
                .build());

        GetItemRequest request = GetItemRequest.builder()
                .key(key_to_get)
                .tableName(tableName)
                .build();

        try {
            Map<String, AttributeValue> returnedItem = dynamoDbClient.getItem(request).item();

            if (returnedItem != null) {
                System.out.println("found item to return");
                return mapToStockedProduct(returnedItem);

            } else {
                System.out.format("No item found with the key %s!\n", id);
                return null;
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return null;
        }

    }




    private StockedProduct mapToStockedProduct(Map<String, AttributeValue> returnedItem) {
        StockedProduct product = new StockedProduct();
        System.out.println(returnedItem.toString());

        if (returnedItem.containsKey("ProductId")) {
            product.setProductId(Integer.parseInt(returnedItem.get("ProductId").n()));
        }
        if (returnedItem.containsKey("Name")) {
            product.setName(returnedItem.get("Name").s());
        }
        if (returnedItem.containsKey("Stock")) {
            product.setStock(Integer.parseInt(returnedItem.get("Stock").n()));
        }
        if (returnedItem.containsKey("Price")) {
            product.setPrice(Double.parseDouble(returnedItem.get("Price").n()));
        }
        if (returnedItem.containsKey("url")) {
            product.setImageUrl(S3_BASE_URL + returnedItem.get("url").s());
        }

        return product;
    }




}
