package com.iecommerce.ecommerce.repository;

import com.iecommerce.ecommerce.entity.Order;
import com.iecommerce.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
