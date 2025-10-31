package com.financeapp.personal_finance_manager.service;

import com.finance.dto.response.CategoryExpenseSummary;
import com.finance.dto.response.DashboardResponse;
import com.finance.model.SavingsGoal;
import com.finance.repository.local.BudgetRepository;
import com.finance.repository.local.ExpenseRepository;
import com.finance.repository.local.IncomeRepository;
import com.finance.repository.local.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    public DashboardResponse getDashboard(Long userId, Integer days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);

        // Calculate totals
        BigDecimal totalIncome = incomeRepository.sumIncomeByUserIdAndDateRange(userId, startDate, endDate);
        BigDecimal totalExpenses = expenseRepository.sumExpensesByUserIdAndDateRange(userId, startDate, endDate);

        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;

        BigDecimal netSavings = totalIncome.subtract(totalExpenses);

        // Get category breakdown
        List<Object[]> categoryData = expenseRepository.findExpenseSummaryByCategory(userId, startDate, endDate);
        List<CategoryExpenseSummary> topExpenseCategories = categoryData.stream()
                .limit(5)
                .map(data -> CategoryExpenseSummary.builder()
                        .categoryId((Long) data[0])
                        .categoryName((String) data[1])
                        .totalAmount((BigDecimal) data[2])
                        .build())
                .collect(Collectors.toList());

        // Count active items
        Integer activeBudgetsCount = budgetRepository.findActiveBudgets(userId, LocalDate.now()).size();
        Integer activeSavingsGoalsCount = savingsGoalRepository
                .findByUserIdAndGoalStatus(userId, SavingsGoal.GoalStatus.ACTIVE).size();

        return DashboardResponse.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netSavings(netSavings)
                .activeBudgetsCount(activeBudgetsCount)
                .activeSavingsGoalsCount(activeSavingsGoalsCount)
                .topExpenseCategories(topExpenseCategories)
                .build();
    }
}
