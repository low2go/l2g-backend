package com.example.l2g.service;

import com.example.l2g.model.receiving.CustomerOrderDTO;
import org.springframework.stereotype.Component;


@Component
public class OrderService {

    public void fulfillCustomerOrder(CustomerOrderDTO orderDTO) {
        //recieves the customer order with all the items they got
        //verifies successful payment
        //creates a fulfillment request for warehouse staff
    }
}
