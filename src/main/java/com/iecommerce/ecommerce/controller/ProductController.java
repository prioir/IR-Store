package com.iecommerce.ecommerce.controller;

import com.iecommerce.ecommerce.entity.Product;
import com.iecommerce.ecommerce.service.CategoryService;
import com.iecommerce.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String products(Model model) {

        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "admin/products";
    }

    @PostMapping("/save")
    public String saveProduct(
            @ModelAttribute Product product,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "imageUrl", required = false) String imageUrl) {

        productService.saveProduct(product, imageFile, imageUrl);

        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "product",
                productService.getProductById(id)
        );

        model.addAttribute(
                "products",
                productService.getAllProducts()
        );

        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );

        return "admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProductById(id);

        return "redirect:/admin/products";
    }
}
