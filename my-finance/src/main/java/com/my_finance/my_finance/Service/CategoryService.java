package com.my_finance.my_finance.Service;

import com.my_finance.my_finance.DTO.CategoryDTO;
import com.my_finance.my_finance.Entity.User;
import com.my_finance.my_finance.Entity.Category;
import com.my_finance.my_finance.Repository.CategoryRepository;
import com.my_finance.my_finance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;


    public List<CategoryDTO> getAllCategoriesByUser(Integer userId) {
        return categoryRepository.findByUserUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = new Category();
        category.setUser(user);
        category.setCategoryName(dto.getCategoryName());
        category.setCategoryType(dto.getCategoryType());
        category.setDescription(dto.getDescription());
        category.setCreatedAt(LocalDateTime.now());
        category.setIsSynced(0);

        Category saved = categoryRepository.save(category);
        return convertToDTO(saved);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setUserId(category.getUser().getUserId());
        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryType(category.getCategoryType());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());   // map createdAt
        dto.setIsSynced(category.getIsSynced());     //  map isSynced
        return dto;
    }
}
