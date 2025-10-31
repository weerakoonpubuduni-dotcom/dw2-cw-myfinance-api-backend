package com.financeapp.personal_finance_manager.service;

import com.finance.dto.response.CategoryExpenseSummary;
import com.finance.dto.response.MonthlyExpenseReport;
import com.finance.repository.local.ExpenseRepository;
import com.finance.repository.local.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    public MonthlyExpenseReport getMonthlyExpenseReport(Long userId, Integer year, Integer month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        BigDecimal totalExpenses = expenseRepository.sumExpensesByUserIdAndDateRange(userId, startDate, endDate);
        if (totalExpenses == null) {
            totalExpenses = BigDecimal.ZERO;
        }

        List<Object[]> categoryData = expenseRepository.findExpenseSummaryByCategory(userId, startDate, endDate);

        BigDecimal finalTotalExpenses = totalExpenses;
        List<CategoryExpenseSummary> categoryBreakdown = categoryData.stream()
                .map(data -> {
                    BigDecimal amount = (BigDecimal) data[2];
                    Double percentage = finalTotalExpenses.compareTo(BigDecimal.ZERO) > 0
                            ? amount.divide(finalTotalExpenses, 4, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal("100")).doubleValue()
                            : 0.0;

                    return CategoryExpenseSummary.builder()
                            .categoryId((Long) data[0])
                            .categoryName((String) data[1])
                            .totalAmount(amount)
                            .percentage(percentage)
                            .build();
                })
                .collect(Collectors.toList());

        BigDecimal averageExpense = totalExpenses.compareTo(BigDecimal.ZERO) > 0
                ? totalExpenses.divide(new BigDecimal(startDate.lengthOfMonth()), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return MonthlyExpenseReport.builder()
                .year(year)
                .month(month)
                .totalExpenses(totalExpenses)
                .averageExpense(averageExpense)
                .categoryBreakdown(categoryBreakdown)
                .build();
    }
}