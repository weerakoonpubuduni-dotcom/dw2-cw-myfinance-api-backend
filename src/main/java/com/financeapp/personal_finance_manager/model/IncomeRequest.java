package com.financeapp.personal_finance_manager.model;

@Data
public class IncomeRequest {
    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Income date is required")
    private LocalDate incomeDate;

    private String description;
    private String source;
}
