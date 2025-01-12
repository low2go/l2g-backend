package com.example.l2g.controller;

import com.example.l2g.model.receiving.CustomerOrder;
import com.example.l2g.model.sending.StockedProduct;
import com.example.l2g.service.FirebaseService;
import com.example.l2g.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final FirebaseService firebaseService;

    @Autowired
    public OrderController(OrderService orderService, FirebaseService firebaseService) {
        this.orderService = orderService;
        this.firebaseService = firebaseService;
    }


    // Handle POST request to create a new order
    @PostMapping("/create")
    public ResponseEntity<CustomerOrder> createOrder(
            @RequestHeader("uid") String uid,
            @RequestHeader("token") String token,
            @Valid @RequestBody CustomerOrder order) {

        //add auth verification
        if (!firebaseService.validateUser(token, uid)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        orderService.createCustomerOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
