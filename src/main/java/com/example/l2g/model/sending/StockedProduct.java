package com.example.l2g.model.sending;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class StockedProduct {

    private String productId;            // Product ID
    private String name;

    @DynamoDbPartitionKey
    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
