package com.example.l2g.model.receiving;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class CustomerOrder {
    private long orderId;
    private String customerName;
    //String Field is the product id, double is the quantity of the product
    private Map<String, Double> products;
}
