package com.example.l2g.controller;


import com.example.l2g.model.sending.StockedProduct;
import com.example.l2g.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final ProductService productService;

    @Autowired
    public CatalogController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<StockedProduct> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public StockedProduct getProductByProductId(@PathVariable String productId) {
        System.out.println("received request");
        return productService.getProductByProductId(productId);
    }
}
