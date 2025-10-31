package com.financeapp.personal_finance_manager.service;

import com.financeapp.personal_finance_manager.entity.Budget;
import com.financeapp.personal_finance_manager.entity.BudgetRepository;
import com.financeapp.personal_finance_manager.entity.CategoryRepository;
import com.financeapp.personal_finance_manager.entity.ExpenseRepository;
import com.financeapp.personal_finance_manager.model.BudgetRequest;
import com.financeapp.personal_finance_manager.model.BudgetResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<BudgetResponse> getAllBudgets(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserIdOrderByStartDateDesc(userId);
        return budgets.stream()
                .map(budget -> mapToResponse(budget, userId))
                .collect(Collectors.toList());
    }

    public List<BudgetResponse> getActiveBudgets(Long userId) {
        List<Budget> budgets = budgetRepository.findActiveBudgets(userId, LocalDate.now());
        return budgets.stream()
                .map(budget -> mapToResponse(budget, userId))
                .collect(Collectors.toList());
    }

    public BudgetResponse getBudgetById(Long budgetId, Long userId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        if (!budget.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return mapToResponse(budget, userId);
    }

    @Transactional
    public BudgetResponse createBudget(BudgetRequest request, Long userId) {
        // Verify category
        categoryRepository.findById(request.getCategoryId())
                .filter(c -> c.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Check for duplicate budget
        budgetRepository.findByUserIdAndCategoryIdAndStartDate(
                userId, request.getCategoryId(), request.getStartDate()
        ).ifPresent(b -> {
            throw new RuntimeException("Budget already exists for this category and period");
        });

        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setCategoryId(request.getCategoryId());
        budget.setBudgetAmount(request.getBudgetAmount());
        budget.setBudgetPeriod(request.getBudgetPeriod());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setAlertThreshold(request.getAlertThreshold() != null ?
                request.getAlertThreshold() : new BigDecimal("80.00"));
        budget.setIsSynced(false);

        Budget savedBudget = budgetRepository.save(budget);
        return mapToResponse(savedBudget, userId);
    }

    @Transactional
    public BudgetResponse updateBudget(Long budgetId, BudgetRequest request, Long userId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        if (!budget.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        budget.setBudgetAmount(request.getBudgetAmount());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setAlertThreshold(request.getAlertThreshold());
        budget.setIsSynced(false);

        Budget updatedBudget = budgetRepository.save(budget);
        return mapToResponse(updatedBudget, userId);
    }

    @Transactional
    public void deleteBudget(Long budgetId, Long userId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        if (!budget.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        budgetRepository.delete(budget);
    }

    private BudgetResponse mapToResponse(Budget budget, Long userId) {
        BigDecimal spentAmount = expenseRepository.sumExpensesByCategoryAndDateRange(
                userId,
                budget.getCategoryId(),
                budget.getStartDate(),
                budget.getEndDate()
        );

        if (spentAmount == null) {
            spentAmount = BigDecimal.ZERO;
        }

        BigDecimal remainingAmount = budget.getBudgetAmount().subtract(spentAmount);
        Double usagePercentage = spentAmount
                .divide(budget.getBudgetAmount(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .doubleValue();

        String status;
        if (usagePercentage > 100) {
            status = "EXCEEDED";
        } else if (usagePercentage >= budget.getAlertThreshold().doubleValue()) {
            status = "WARNING";
        } else {
            status = "ON_TRACK";
        }

        return BudgetResponse.builder()
                .budgetId(budget.getBudgetId())
                .categoryId(budget.getCategoryId())
                .categoryName(budget.getCategory() != null ? budget.getCategory().getCategoryName() : null)
                .budgetAmount(budget.getBudgetAmount())
                .spentAmount(spentAmount)
                .remainingAmount(remainingAmount)
                .usagePercentage(usagePercentage)
                .budgetPeriod(budget.getBudgetPeriod())
                .startDate(budget.getStartDate())
                .endDate(budget.getEndDate())
                .alertThreshold(budget.getAlertThreshold())
                .status(status)
                .build();
    }
}
