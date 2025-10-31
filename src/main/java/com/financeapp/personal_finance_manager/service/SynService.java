package com.financeapp.personal_finance_manager.service;

import com.finance.dto.response.SyncResponse;
import com.finance.model.*;
import com.finance.repository.local.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SyncService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SyncLogRepository syncLogRepository;

    @Transactional
    public SyncResponse syncAllData(Long userId) {
        int recordsSynced = 0;
        int recordsFailed = 0;
        StringBuilder details = new StringBuilder();

        try {
            // Sync Categories
            List<Category> unsyncedCategories = categoryRepository.findByUserIdAndIsSynced(userId, false);
            for (Category category : unsyncedCategories) {
                try {
                    // TODO: Push to Oracle database
                    category.setIsSynced(true);
                    categoryRepository.save(category);
                    recordsSynced++;
                } catch (Exception e) {
                    recordsFailed++;
                    logSyncError(userId, "categories", category.getCategoryId(), e.getMessage());
                }
            }
            details.append("Categories: ").append(unsyncedCategories.size()).append(" synced. ");

            // Sync Expenses
            List<Expense> unsyncedExpenses = expenseRepository.findByUserIdAndIsSynced(userId, false);
            for (Expense expense : unsyncedExpenses) {
                try {
                    // TODO: Push to Oracle database
                    expense.setIsSynced(true);
                    expense.setSyncTimestamp(LocalDateTime.now());
                    expenseRepository.save(expense);
                    recordsSynced++;
                } catch (Exception e) {
                    recordsFailed++;
                    logSyncError(userId, "expenses", expense.getExpenseId(), e.getMessage());
                }
            }
            details.append("Expenses: ").append(unsyncedExpenses.size()).append(" synced. ");

            // Sync Income
            List<Income> unsyncedIncome = incomeRepository.findByUserIdAndIsSynced(userId, false);
            for (Income income : unsyncedIncome) {
                try {
                    // TODO: Push to Oracle database
                    income.setIsSynced(true);
                    income.setSyncTimestamp(LocalDateTime.now());
                    incomeRepository.save(income);
                    recordsSynced++;
                } catch (Exception e) {
                    recordsFailed++;
                    logSyncError(userId, "income", income.getIncomeId(), e.getMessage());
                }
            }
            details.append("Income: ").append(unsyncedIncome.size()).append(" synced. ");

            // Sync Budgets
            List<Budget> unsyncedBudgets = budgetRepository.findByUserIdAndIsSynced(userId, false);
            for (Budget budget : unsyncedBudgets) {
                try {
                    // TODO: Push to Oracle database
                    budget.setIsSynced(true);
                    budgetRepository.save(budget);
                    recordsSynced++;
                } catch (Exception e) {
                    recordsFailed++;
                    logSyncError(userId, "budgets", budget.getBudgetId(), e.getMessage());
                }
            }
            details.append("Budgets: ").append(unsyncedBudgets.size()).append(" synced. ");

            // Sync Savings Goals
            List<SavingsGoal> unsyncedGoals = savingsGoalRepository.findByUserIdAndIsSynced(userId, false);
            for (SavingsGoal goal : unsyncedGoals) {
                try {
                    // TODO: Push to Oracle database
                    goal.setIsSynced(true);
                    savingsGoalRepository.save(goal);
                    recordsSynced++;
                } catch (Exception e) {
                    recordsFailed++;
                    logSyncError(userId, "savings_goals", goal.getGoalId(), e.getMessage());
                }
            }
            details.append("Savings Goals: ").append(unsyncedGoals.size()).append(" synced.");

            String status = recordsFailed == 0 ? "SUCCESS" : (recordsSynced > 0 ? "PARTIAL" : "FAILED");

            return SyncResponse.builder()
                    .status(status)
                    .message("Synchronization completed")
                    .recordsSynced(recordsSynced)
                    .recordsFailed(recordsFailed)
                    .syncTimestamp(LocalDateTime.now())
                    .details(details.toString())
                    .build();

        } catch (Exception e) {
            return SyncResponse.builder()
                    .status("FAILED")
                    .message("Synchronization failed: " + e.getMessage())
                    .recordsSynced(recordsSynced)
                    .recordsFailed(recordsFailed)
                    .syncTimestamp(LocalDateTime.now())
                    .details(details.toString())
                    .build();
        }
    }

    private void logSyncError(Long userId, String tableName, Long recordId, String errorMessage) {
        SyncLog log = new SyncLog();
        log.setUserId(userId);
        log.setTableName(tableName);
        log.setRecordId(recordId);
        log.setOperation(SyncLog.Operation.INSERT);
        log.setSyncStatus(SyncLog.SyncStatus.FAILED);
        log.setErrorMessage(errorMessage);
        syncLogRepository.save(log);
    }

    public List<SyncLog> getSyncHistory(Long userId) {
        return syncLogRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}