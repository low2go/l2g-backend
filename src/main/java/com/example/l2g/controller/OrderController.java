package com.example.l2g.controller;

import com.example.l2g.model.receiving.CustomerOrderDTO;
import com.example.l2g.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    // Handle POST request to create a new order
    @PostMapping
    public ResponseEntity<CustomerOrderDTO> createOrder(@Valid @RequestBody CustomerOrderDTO order) {
        orderService.createOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
