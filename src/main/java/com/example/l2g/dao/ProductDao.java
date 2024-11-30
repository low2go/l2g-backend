package com.example.l2g.dao;

import com.example.l2g.model.sending.StockedProduct;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.util.HashMap;
import java.util.Map;


@Repository
public class ProductDao {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName = "Products";
    private final String partitionKey = "ProductId";

    public ProductDao(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
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
            product.setProductId(returnedItem.get("ProductId").n());  // Map id
        }

        // Map other attributes, if they exist (like "name" in this case)
        if (returnedItem.containsKey("Name")) {
            product.setName(returnedItem.get("Name").s());  // Map name
        }
        return product;
    }



}
