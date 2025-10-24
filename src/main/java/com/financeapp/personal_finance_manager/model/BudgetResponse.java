package com.financeapp.personal_finance_manager.model;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {
    private Long budgetId;
    private Long categoryId;
    private String categoryName;
    private BigDecimal budgetAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;
    private Double usagePercentage;
    private Budget.BudgetPeriod budgetPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal alertThreshold;
    private String status; // ON_TRACK, WARNING, EXCEEDED
}

