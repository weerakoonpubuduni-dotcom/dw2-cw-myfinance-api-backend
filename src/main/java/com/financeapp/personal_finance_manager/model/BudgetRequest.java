package com.financeapp.personal_finance_manager.model;

@Data
public class BudgetRequest {
    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Budget amount is required")
    @Positive(message = "Budget amount must be positive")
    private BigDecimal budgetAmount;

    @NotNull(message = "Budget period is required")
    private Budget.BudgetPeriod budgetPeriod;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private BigDecimal alertThreshold;
}

