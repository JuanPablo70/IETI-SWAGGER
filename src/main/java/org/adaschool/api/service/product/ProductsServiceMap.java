package org.adaschool.api.service.product;

import org.adaschool.api.repository.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceMap implements ProductsService {
    @Override
    public Product save(Product product) {
        return new Product("1", product.getName(), product.getDescription(), product.getCategory(), product.getPrice());
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.of(new Product("1", "ProductName", "ProductDescription", "ProductCategory", 1234));
    }

    @Override
    public List<Product> all() {
        return List.of(new Product("1", "ProductName", "ProductDescription", "ProductCategory", 1234));
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Product update(Product product, String productId) {
        return null;
    }
}
