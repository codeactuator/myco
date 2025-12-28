package com.myco.users.services;

import com.myco.users.entities.Order;
import com.myco.users.entities.ProductInstance;
import com.myco.users.repositories.OrderRepository;
import com.myco.users.repositories.ProductInstanceRepository;
import com.myco.users.repositories.ProductRepository;
import com.myco.users.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInstanceRepository productInstanceRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order orderRequest) {
        if (orderRequest.getVendor() != null && orderRequest.getVendor().getId() != null) {
             orderRequest.setVendor(vendorRepository.findById(orderRequest.getVendor().getId())
                 .orElseThrow(() -> new RuntimeException("Vendor not found")));
        }

        if (orderRequest.getProduct() != null && orderRequest.getProduct().getId() != null) {
            orderRequest.setProduct(productRepository.findById(orderRequest.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found")));
        }

        orderRequest.setCreatedAt(LocalDateTime.now());
        
        List<String> qrcodes = new ArrayList<>();
        List<ProductInstance> productInstances = new ArrayList<>();

        for (int i = 0; i < orderRequest.getQuantity(); i++) {
            UUID qrUuid = UUID.randomUUID();
            qrcodes.add(qrUuid.toString());
            
            ProductInstance instance = new ProductInstance();
            instance.setId(qrUuid);
            instance.setProduct(orderRequest.getProduct());
            productInstances.add(instance);
        }
        productInstanceRepository.saveAll(productInstances);
        orderRequest.setQrcodes(qrcodes);

        return orderRepository.save(orderRequest);
    }
    
    public Order getOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}