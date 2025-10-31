package com.financeapp.personal_finance_manager.service;

import com.finance.dto.request.CategoryRequest;
import com.finance.model.Category;
import com.finance.repository.local.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    public List<Category> getCategoriesByType(Long userId, Category.CategoryType type) {
        return categoryRepository.findByUserIdAndCategoryType(userId, type);
    }

    public Category getCategoryById(Long categoryId, Long userId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return category;
    }

    @Transactional
    public Category createCategory(CategoryRequest request, Long userId) {
        // Check for duplicate
        categoryRepository.findByUserIdAndCategoryName(userId, request.getCategoryName())
                .ifPresent(c -> {
                    throw new RuntimeException("Category with this name already exists");
                });

        Category category = new Category();
        category.setUserId(userId);
        category.setCategoryName(request.getCategoryName());
        category.setCategoryType(request.getCategoryType());
        category.setDescription(request.getDescription());
        category.setIsSynced(false);

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long categoryId, CategoryRequest request, Long userId) {
        Category category = getCategoryById(categoryId, userId);

        // Check for duplicate name (excluding current category)
        categoryRepository.findByUserIdAndCategoryName(userId, request.getCategoryName())
                .ifPresent(c -> {
                    if (!c.getCategoryId().equals(categoryId)) {
                        throw new RuntimeException("Category with this name already exists");
                    }
                });

        category.setCategoryName(request.getCategoryName());
        category.setCategoryType(request.getCategoryType());
        category.setDescription(request.getDescription());
        category.setIsSynced(false);

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId, Long userId) {
        Category category = getCategoryById(categoryId, userId);
        categoryRepository.delete(category);
    }
}





