package com.example.l2g.service;

import com.example.l2g.dao.OrdersDao;
import com.example.l2g.dao.ProductDao;
import com.example.l2g.model.receiving.CustomerOrder;
import com.example.l2g.model.sending.OrderItem;
import com.example.l2g.model.sending.OrderToFulfill;
import com.example.l2g.model.sending.StockedProduct;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.endpoints.internal.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class OrderService {

    private final ProductDao productDao;
    private final ProductService productService;
    private final OrdersDao ordersDao;

    public OrderService(ProductDao productDao, ProductService productService, OrdersDao ordersDao) {
        this.productDao = productDao;
        this.productService = productService;
        this.ordersDao = ordersDao;
    }

    public void createCustomerOrder(CustomerOrder incomingOrder) {
        Map<String, Integer> orderMap = incomingOrder.getOrderIdAndQuantityMap();
        List<String> productIds = new ArrayList<>(orderMap.keySet());
        List<StockedProduct> currentProductInformation = productService.getStockedProductsFromProductIds(productIds);

        OrderToFulfill orderToFulfill = new OrderToFulfill();

        orderToFulfill.setCustomerId("test123");
        orderToFulfill.setOrderStatus("ready");
        orderToFulfill.setOrderDate(Instant.now());

        double totalPrice = 0;

        for(StockedProduct product: currentProductInformation) {
            String productId = product.getProductId();
            int quantity = orderMap.get(productId);
            double price = product.getPrice();
            orderToFulfill.addItem(new OrderItem(productId, quantity, price, price * quantity));
            totalPrice += price * quantity;
        }

        //removes floating point errors
        totalPrice = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        orderToFulfill.setTotalAmount(totalPrice);
        System.out.println(orderToFulfill.toString());

        //add orderId to userID table

        //add order to orders table
        ordersDao.addOrder(orderToFulfill);

        //add order reference to users table
    }

    private void verifyAuthToken(String authToken) {

    }

    private void verifyPayment() {

    }

}
