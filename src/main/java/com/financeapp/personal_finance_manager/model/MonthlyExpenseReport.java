package com.financeapp.personal_finance_manager.model;

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
