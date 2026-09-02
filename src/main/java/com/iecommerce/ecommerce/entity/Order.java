package com.iecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;

    private String status;

    private LocalDateTime orderDate;

    // Delivery Information
    private String phone;

    private String address;

    private String city;

    // Payment Information
    private String paymentMethod;

    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}