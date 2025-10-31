package com.financeapp.personal_finance_manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netSavings;
    private BigDecimal totalBudget;
    private BigDecimal budgetUtilization;
    private Integer activeBudgetsCount;
    private Integer activeSavingsGoalsCount;
    private List<CategoryExpenseSummary> topExpenseCategories;
    private List<BudgetResponse> budgetsNearLimit;
}
