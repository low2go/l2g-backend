package com.example.l2g.controller;


import com.example.l2g.model.sending.StockedProduct;
import com.example.l2g.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final ProductService productService;

    @Autowired
    public CatalogController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<StockedProduct> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/{productId}")
    public StockedProduct getProductByProductId(@PathVariable String productId) {
        System.out.println("received request");

        if (!productId.matches("\\d+")) { // Check if productId contains only digits
            throw new IllegalArgumentException("Product ID must be numeric.");
        }

        return productService.getProductByProductId(productId);
    }

    @GetMapping("/search")
    public List<StockedProduct> search(@RequestParam String query) {
        return productService.searchDBFromQuery(query);
    }

}
