package com.example.l2g.dao;

import com.example.l2g.model.sending.OrderItem;
import com.example.l2g.model.sending.OrderToFulfill;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrdersDao {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName = "Orders";

    @Value("${awsS3.baseurl}")
    private String S3_BASE_URL;

    public OrdersDao(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    /**
     * Adds a full order object to the Orders table.
     *
     * @param order The OrderToFulfill object to be written.
     */
    public void addOrder(OrderToFulfill order) {
        Map<String, AttributeValue> item = mapOrderToItem(order);

        // Build the PutItemRequest
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        // Write the item to DynamoDB
        dynamoDbClient.putItem(putItemRequest);
    }

    /**
     * Maps the OrderToFulfill object to a DynamoDB item (key-value pairs).
     *
     * @param order The order to be mapped.
     * @return A map representing the DynamoDB item.
     */
    private Map<String, AttributeValue> mapOrderToItem(OrderToFulfill order) {
        Map<String, AttributeValue> item = new HashMap<>();

        // Map basic fields
        item.put("OrderId", AttributeValue.builder().s(order.getOrderId()).build());
        item.put("CustomerId", AttributeValue.builder().s(order.getCustomerId()).build());
        item.put("OrderStatus", AttributeValue.builder().s(order.getOrderStatus()).build());
        item.put("OrderDate", AttributeValue.builder().s(order.getOrderDate().toString()).build());
        item.put("TotalAmount", AttributeValue.builder().n(String.valueOf(order.getTotalAmount())).build());

        // Map items list if available
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            item.put("Items", mapItems(order.getItems()));
        }

        return item;
    }

    /**
     * Maps the list of OrderItem objects to DynamoDB-compatible format.
     *
     * @param items The list of items to be mapped.
     * @return A DynamoDB list attribute value.
     */
    private AttributeValue mapItems(List<OrderItem> items) {
        List<AttributeValue> itemList = items.stream()
                .map(this::mapOrderItem)
                .toList();
        return AttributeValue.builder().l(itemList).build();
    }

    /**
     * Maps an individual OrderItem to DynamoDB-compatible format.
     *
     * @param item The OrderItem to be mapped.
     * @return A DynamoDB map attribute value for the OrderItem.
     */
    private AttributeValue mapOrderItem(OrderItem item) {
        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put("Product", AttributeValue.builder().s(item.getProduct()).build());
        itemMap.put("Quantity", AttributeValue.builder().n(String.valueOf(item.getQuantity())).build());
        itemMap.put("PriceAtTime", AttributeValue.builder().n(String.valueOf(item.getPriceAtTime())).build());
        itemMap.put("Subtotal", AttributeValue.builder().n(String.valueOf(item.getSubtotal())).build());

        return AttributeValue.builder().m(itemMap).build();
    }
}
