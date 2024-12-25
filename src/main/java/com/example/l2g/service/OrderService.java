package com.example.l2g.service;

import com.example.l2g.model.receiving.CustomerOrder;
import com.example.l2g.model.sending.OrderItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class OrderService {

    public void fulfillCustomerOrder(CustomerOrder orderDTO) {



        //recieves the customer order with all the items they got
        //verifies successful payment
        //creates a fulfillment request for warehouse staff
    }

    private List<OrderItem> getOrderItemsFromOrderedProducts(Map<String, Double> products) {
        List<String> productIds = new ArrayList<>(products.keySet());

        return null;
    }
}
