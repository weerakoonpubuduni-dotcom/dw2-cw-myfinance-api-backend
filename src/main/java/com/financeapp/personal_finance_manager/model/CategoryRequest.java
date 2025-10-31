package com.financeapp.personal_finance_manager.model;

import com.financeapp.personal_finance_manager.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotNull(message = "Category type is required")
    private Category.CategoryType categoryType;

    private String description;
}
