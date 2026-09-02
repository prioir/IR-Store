package com.iecommerce.ecommerce.controller;

import com.iecommerce.ecommerce.entity.Category;
import com.iecommerce.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String categories(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("category",new Category());

        return "admin/categories";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category){
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model){
        model.addAttribute("category",categoryService.getCategoryById(id));
        model.addAttribute("categories",categoryService.getAllCategories());

        return "admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }


}
