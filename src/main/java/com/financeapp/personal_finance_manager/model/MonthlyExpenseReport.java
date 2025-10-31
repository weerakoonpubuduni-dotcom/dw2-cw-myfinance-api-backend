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
public class MonthlyExpenseReport {
    private Integer year;
    private Integer month;
    private BigDecimal totalExpenses;
    private BigDecimal averageExpense;
    private List<CategoryExpenseSummary> categoryBreakdown;
}
