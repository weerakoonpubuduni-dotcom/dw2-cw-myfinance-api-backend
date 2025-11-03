package com.my_finance.my_finance.Controller;

import com.my_finance.my_finance.DTO.CategoryDTO;
import com.my_finance.my_finance.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/categories")
//@CrossOrigin --- Front end link
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping ("/users/{userId}")
    public List<CategoryDTO> getAllCategoriesByUser(@PathVariable Integer userId) {
        return categoryService.getAllCategoriesByUser(userId);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryDTO updatedCategory) {
        CategoryDTO updated = categoryService.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
