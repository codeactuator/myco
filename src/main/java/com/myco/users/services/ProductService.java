package com.myco.users.services;

import com.myco.users.entities.Product;
import com.myco.users.entities.ProductInstance;
import com.myco.users.entities.ProductRegistration;
import com.myco.users.repositories.ProductInstanceRepository;
import com.myco.users.repositories.ProductRegistrationRepository;
import com.myco.users.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInstanceRepository productInstanceRepository;
    @Autowired
    private ProductRegistrationRepository productRegistrationRepository;

    public void registerProduct(UUID userId, UUID qrUuid) {
        if (productRegistrationRepository.existsByProductInstanceId(qrUuid)) {
            throw new RuntimeException("Product already registered");
        }

        ProductInstance instance = productInstanceRepository.findById(qrUuid)
                .orElseThrow(() -> new RuntimeException("Invalid Product Code"));

        ProductRegistration reg = new ProductRegistration();
        reg.setUserId(userId);
        reg.setProductInstance(instance);
        reg.setRegistrationDate(LocalDateTime.now());

        productRegistrationRepository.save(reg);
    }

    public UUID getProductOwner(UUID productInstanceId) {
        return productRegistrationRepository.findByProductInstanceId(productInstanceId)
                .map(ProductRegistration::getUserId)
                .orElseThrow(() -> new RuntimeException("Product not registered"));
    }

    public List<Map<String, Object>> getUserProducts(UUID userId) {
        return productRegistrationRepository.findByUserId(userId).stream()
                .map(reg -> {
                    Map<String, Object> map = new HashMap<>();
                    Product p = reg.getProductInstance().getProduct();
                    map.put("id", reg.getProductInstance().getId());
                    map.put("name", p.getName());
                    map.put("description", p.getDescription());
                    map.put("imageUrl", p.getImageUrl());
                    map.put("registrationDate", reg.getRegistrationDate());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getVendorProducts(UUID vendorId) {
        return productRepository.findByVendorId(vendorId).stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", p.getId());
                    map.put("name", p.getName());
                    map.put("description", p.getDescription());
                    map.put("imageUrl", p.getImageUrl());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, Product productDetails) {
        Product product = getProduct(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setImageUrl(productDetails.getImageUrl());
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}