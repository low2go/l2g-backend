package com.example.l2g.controller;

import com.example.l2g.model.receiving.CustomerOrder;
import com.example.l2g.model.sending.OrderToFulfill;
import com.example.l2g.model.sending.StockedProduct;
import com.example.l2g.service.FirebaseService;
import com.example.l2g.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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


        orderService.createCustomerOrder(order, uid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/user_orders")
    public ResponseEntity<List<OrderToFulfill>> getUserOrders(
            @RequestHeader("uid") String uid,
            @RequestHeader("token") String token) {

        if (!firebaseService.validateUser(token, uid)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<OrderToFulfill> orders = orderService.getUserOrders(uid);

        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }

        return ResponseEntity.ok(orders);
    }



}
