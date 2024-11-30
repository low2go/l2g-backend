package com.example.l2g.model.receiving;

import lombok.Data;
import java.util.List;

@Data
public class CustomerOrderDTO {
    private long orderId;
    private String customerName;
    private List<OrderedProduct> products;
}
