package com.iecommerce.ecommerce.controller;

import com.iecommerce.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;

    @GetMapping
    public String dashboard(){
        return "admin/dashboard";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/orders";
    }


    @PostMapping("/orders/update-status/{id}")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        orderService.updateOrderStatus(id, status);

        return "redirect:/admin/orders";
    }

    @PostMapping("/orders/update-payment/{id}")
    public String updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String paymentStatus) {

        orderService.updatePaymentStatus(id, paymentStatus);

        return "redirect:/admin/orders";
    }

}
