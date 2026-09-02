package com.iecommerce.ecommerce.repository;

import com.iecommerce.ecommerce.entity.CartItem;
import com.iecommerce.ecommerce.entity.Product;
import com.iecommerce.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByUser(User user);

    CartItem findByUserAndProduct(User user, com.iecommerce.ecommerce.entity.Product product);
}
