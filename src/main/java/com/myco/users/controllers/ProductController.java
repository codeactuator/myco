package com.myco.users.controllers;

import com.myco.users.entities.Product;
import com.myco.users.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@RequestBody Map<String, String> payload) {
        try {
            UUID userId = UUID.fromString(payload.get("userId"));
            UUID productId = UUID.fromString(payload.get("productInstanceId"));
            productService.registerProduct(userId, productId);
            return ResponseEntity.ok(Map.of("message", "Product registered successfully"));
        } catch (Exception e) {
            String message = e.getMessage();
            if (message != null && message.contains("Product already registered")) {
                message = "Product already registered";
            }
            return ResponseEntity.badRequest().body(Map.of("message", message != null ? message : "Registration failed"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getUserProducts(@PathVariable UUID userId) {
        return ResponseEntity.ok(productService.getUserProducts(userId));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<Map<String, Object>>> getVendorProducts(@PathVariable UUID vendorId) {
        return ResponseEntity.ok(productService.getVendorProducts(vendorId));
    }

    @GetMapping("/owner/{productInstanceId}")
    public ResponseEntity<UUID> getProductOwner(@PathVariable UUID productInstanceId) {
        return ResponseEntity.ok(productService.getProductOwner(productInstanceId));
    }

    @GetMapping("/short-code/{productInstanceId}")
    public ResponseEntity<Map<String, String>> getProductShortCode(@PathVariable UUID productInstanceId) {
        String code = productService.getShortCode(productInstanceId);
        return ResponseEntity.ok(Map.of("shortCode", code != null ? code : ""));
    }

    @PostMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadProductImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        productService.uploadImage(id, file);
        return ResponseEntity.ok().build();
    }
}