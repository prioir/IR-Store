package com.iecommerce.ecommerce.service;


import com.iecommerce.ecommerce.entity.CartItem;
import com.iecommerce.ecommerce.entity.Product;
import com.iecommerce.ecommerce.entity.User;
import com.iecommerce.ecommerce.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public void addToCart(User user, Product product) {

        CartItem item= cartItemRepository.findByUserAndProduct(user, product);

        if (item != null) {

            item.setQuantity(item.getQuantity() + 1);
        }else {
            item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(1);
        }

        cartItemRepository.save(item);

    }


    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }


    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }



    public void increaseQuantity(Long id) {

        CartItem item = cartItemRepository.findById(id).orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
            cartItemRepository.save(item);
        }
    }


    public void decreaseQuantity(Long id) {

        CartItem item = cartItemRepository.findById(id).orElse(null);

        if (item != null) {

            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                cartItemRepository.save(item);
            }

        }
    }



}
