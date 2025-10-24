package com.financeapp.personal_finance_manager.model;

@Data
public class ExpenseRequest {
    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;

    private String description;
    private String paymentMethod;
}

