package org.adaschool.api.controller.product;

import org.adaschool.api.exception.ProductNotFoundException;
import org.adaschool.api.repository.product.Product;
import org.adaschool.api.repository.product.ProductDto;
import org.adaschool.api.service.product.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products/")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(@Autowired ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product productToSave = new Product(productDto);
        Product productSaved = productsService.save(productToSave);
        URI createdProductUri = URI.create(productSaved.toString());
        return ResponseEntity.created(createdProductUri).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productsService.all();
        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") String id) {
        Optional<Product> product = productsService.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        return ResponseEntity.ok(product.get());
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") String id) {
        Optional<Product> productInDb = productsService.findById(id);
        if (productInDb.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        productInDb.get().update(productDto);
        productsService.save(productInDb.get());
        return ResponseEntity.ok(productInDb.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        Optional<Product> productInDb = productsService.findById(id);
        if (productInDb.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        productsService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
