package com.example.l2g.controller;

import com.example.l2g.model.receiving.CustomerOrder;
import com.example.l2g.model.sending.StockedProduct;
import com.example.l2g.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CustomerOrder> createOrder(@Valid @RequestBody CustomerOrder order) {
//        orderService.createOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
//
//    @PostMapping("/batch")
//    public List<StockedProduct> getBatchProducts(@RequestBody CustomerOrder order) {
//        // Fetch batch products from the service
//        return productService.
//    }

}
