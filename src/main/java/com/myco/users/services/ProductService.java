package com.myco.users.services;

import com.myco.users.entities.Product;
import com.myco.users.repositories.ProductRepository;
import com.myco.users.entities.Vendor;
import com.myco.users.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        if (product.getVendor() != null && product.getVendor().getId() != null) {
            Vendor vendor = vendorRepository.findById(product.getVendor().getId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
            product.setVendor(vendor);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setImageUrl(productDetails.getImageUrl());
        product.setStatus(productDetails.getStatus());

        if (productDetails.getVendor() != null && productDetails.getVendor().getId() != null) {
            Vendor vendor = vendorRepository.findById(productDetails.getVendor().getId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
            product.setVendor(vendor);
        }

        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByVendor(UUID vendorId) {
        return productRepository.findByVendorId(vendorId);
    }
}