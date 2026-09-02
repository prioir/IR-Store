package com.iecommerce.ecommerce.controller;


import com.iecommerce.ecommerce.repository.CategoryRepository;
import com.iecommerce.ecommerce.repository.ProductRepository;
import com.iecommerce.ecommerce.service.CategoryService;
import com.iecommerce.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "home";
    }

    @GetMapping("/products")
    public String products(Model model) {

        model.addAttribute("products", productService.getAllProducts());

        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {

        model.addAttribute("product", productService.getProductById(id));

        return "product-details";
    }


}
