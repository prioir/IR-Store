package com.iecommerce.ecommerce.service;


import com.iecommerce.ecommerce.entity.CartItem;
import com.iecommerce.ecommerce.entity.Order;
import com.iecommerce.ecommerce.entity.Product;
import com.iecommerce.ecommerce.entity.User;
import com.iecommerce.ecommerce.repository.CartItemRepository;
import com.iecommerce.ecommerce.repository.OrderRepository;
import com.iecommerce.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public Order placeOrder(
            User user,
            String phone,
            String address,
            String city,
            String paymentMethod) {

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            return null;
        }

        // ================= STOCK CHECK =================

        for (CartItem item : cartItems) {

            if (item.getProduct().getStock() < item.getQuantity()) {

                throw new RuntimeException(
                        "Not enough stock for "
                                + item.getProduct().getName()
                );
            }
        }


        // ================= CALCULATE TOTAL =================

        double total = 0;

        for (CartItem item : cartItems) {

            total += item.getProduct().getPrice()
                    * item.getQuantity();
        }


        // ================= CREATE ORDER =================

        Order order = new Order();

        order.setUser(user);
        order.setTotalPrice(total);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        // Delivery Information
        order.setPhone(phone);
        order.setAddress(address);
        order.setCity(city);

        // Payment Information
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus("UNPAID");


        // ================= DECREASE STOCK =================

        for (CartItem item : cartItems) {

            item.getProduct().setStock(
                    item.getProduct().getStock()
                            - item.getQuantity()
            );

            productRepository.save(item.getProduct());
        }


        // ================= SAVE ORDER =================

        Order savedOrder = orderRepository.save(order);


        // ================= CLEAR CART =================

        cartItemRepository.deleteAll(cartItems);


        return savedOrder;
    }

    public List<Order> getUserOrders(User user) {

        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public void updateOrderStatus(Long id, String status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setStatus(status);

        orderRepository.save(order);
    }

    public void updatePaymentStatus(Long id, String paymentStatus) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setPaymentStatus(paymentStatus);

        orderRepository.save(order);
    }

}


