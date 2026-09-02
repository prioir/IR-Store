package com.iecommerce.ecommerce.controller;


import com.iecommerce.ecommerce.entity.Order;
import com.iecommerce.ecommerce.entity.Product;
import com.iecommerce.ecommerce.entity.User;
import com.iecommerce.ecommerce.repository.ProductRepository;
import com.iecommerce.ecommerce.repository.UserRepository;
import com.iecommerce.ecommerce.service.CartItemService;
import com.iecommerce.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartItemService cartItemService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;


    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, org.springframework.security.core.Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName());
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null && user != null) {
            cartItemService.addToCart(user, product);
        }
        return "redirect:/cart";
    }

    @GetMapping
    public String cart(Authentication authentication, Model model) {

        User user = userRepository.findByEmail(authentication.getName());

        model.addAttribute("cartItems", cartItemService.getCartItems(user));

        return "cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return "redirect:/cart";
    }


    @PostMapping("/increase/{id}")
    public String increaseQuantity(@PathVariable Long id) {

        cartItemService
                .increaseQuantity(id);

        return "redirect:/cart";
    }


    @PostMapping("/decrease/{id}")
    public String decreaseQuantity(@PathVariable Long id) {

        cartItemService.decreaseQuantity(id);

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Authentication authentication, Model model) {

        User user = userRepository.findByEmail(authentication.getName());

        model.addAttribute("cartItems",
                cartItemService.getCartItems(user));

        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(
            Authentication authentication,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String paymentMethod,
            Model model) {

        User user = userRepository.findByEmail(authentication.getName());

        try {

            Order order = orderService.placeOrder(
                    user,
                    phone,
                    address,
                    city,
                    paymentMethod
            );

            if (order == null) {

                model.addAttribute(
                        "error",
                        "Your cart is empty."
                );

                model.addAttribute(
                        "cartItems",
                        cartItemService.getCartItems(user)
                );

                return "checkout";
            }

            // Send order to confirmation page
            model.addAttribute("order", order);

            return "order-confirmation";

        } catch (RuntimeException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            model.addAttribute(
                    "cartItems",
                    cartItemService.getCartItems(user)
            );

            return "checkout";
        }
    }

    @GetMapping("/orders")
    public String orders(Authentication authentication, Model model) {

        User user = userRepository.findByEmail(authentication.getName());

        model.addAttribute("orders", orderService.getUserOrders(user));

        return "orders";
    }



}

