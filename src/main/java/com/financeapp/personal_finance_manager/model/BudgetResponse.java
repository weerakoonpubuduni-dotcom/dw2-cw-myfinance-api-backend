package com.financeapp.personal_finance_manager.model;

import com.financeapp.personal_finance_manager.entity.Budget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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

