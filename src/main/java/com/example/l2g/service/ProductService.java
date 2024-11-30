package com.example.l2g.service;

import com.example.l2g.dao.ProductDao;
import com.example.l2g.model.sending.StockedProduct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<StockedProduct> getAllProducts() {
        return null;
    }

    public StockedProduct getProductByProductId(String productId) {
        return productDao.getProductById(productId);
    }
}
