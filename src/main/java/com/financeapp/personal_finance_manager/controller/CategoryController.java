package com.financeapp.personal_finance_manager.controller;

import com.financeapp.personal_finance_manager.entity.Category;
import com.financeapp.personal_finance_manager.entity.CategoryRepository;
import com.financeapp.personal_finance_manager.model.CategoryRequest;
import com.financeapp.personal_finance_manager.model.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    private Long getCurrentUserId() {
        return 1L; // Placeholder
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        Long userId = getCurrentUserId();
        List<Category> categories = categoryRepository.findByUserId(userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<Category>> getCategoriesByType(@PathVariable String type) {
        Long userId = getCurrentUserId();
        Category.CategoryType categoryType = Category.CategoryType.valueOf(type.toUpperCase());
        List<Category> categories = categoryRepository.findByUserIdAndCategoryType(userId, categoryType);
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest request) {
        Long userId = getCurrentUserId();

        Category category = new Category();
        category.setUserId(userId);
        category.setCategoryName(request.getCategoryName());
        category.setCategoryType(request.getCategoryType());
        category.setDescription(request.getDescription());
        category.setIsSynced(false);

        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        categoryRepository.delete(category);
        return ResponseEntity.ok(new MessageResponse("Category deleted successfully"));
    }
}