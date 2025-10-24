package com.financeapp.personal_finance_manager.model;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotNull(message = "Category type is required")
    private Category.CategoryType categoryType;

    private String description;
}
