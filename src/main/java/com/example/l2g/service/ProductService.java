package com.example.l2g.service;

import com.example.l2g.dao.ProductDao;
import com.example.l2g.model.sending.StockedProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<StockedProduct> getAllProducts() {
        return productDao.getAllProducts();
    }

    public StockedProduct getProductByProductId(String productId) {
        return productDao.getProductById(productId);
    }

    public List<StockedProduct> searchDBFromQuery(String query) {
        List<StockedProduct> allProducts = productDao.getAllProducts();
        List<StockedProduct> matchingProducts = new ArrayList<>();

        for (StockedProduct product : allProducts) {
            String productName = product.getName();
            if (productName.toLowerCase().contains(query.toLowerCase())) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    public List<StockedProduct> getOrderItemsFromOrderedProducts(Map<String, Double> products) {
        List<String> productIds = new ArrayList<>(products.keySet());
        return productDao.getProductsByIds(productIds);

    }

}
