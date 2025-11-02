package com.my_finance.my_finance.Service;
import com.my_finance.my_finance.DTO.ExpenseDTO;
import com.my_finance.my_finance.Entity.Budget;
import com.my_finance.my_finance.Entity.Category;
import com.my_finance.my_finance.Entity.Expense;
import com.my_finance.my_finance.Entity.User;
import com.my_finance.my_finance.Repository.CategoryRepository;
import com.my_finance.my_finance.Repository.ExpenseRepository;
import com.my_finance.my_finance.Repository.BudgetRepository;

import com.my_finance.my_finance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    @Autowired private  ExpenseRepository expenseRepository;
    @Autowired private  BudgetRepository budgetRepository;
    @Autowired private  UserRepository userRepository;
    @Autowired private  CategoryRepository categoryRepository;



    // ✅ Create Expense and Deduct from Budget
    public ExpenseDTO createExpense(ExpenseDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(category);
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setDescription(dto.getDescription());
        expense.setPaymentMethod(dto.getPaymentMethod());
        expense.setIsSynced(0);

        // Save the expense
        Expense savedExpense = expenseRepository.save(expense);

        // Deduct from budget (if available)
        deductFromBudget(user.getUserId(), category.getCategoryId(), BigDecimal.valueOf(dto.getAmount()), dto.getExpenseDate());

        return convertToDTO(savedExpense);
    }

    // ✅ Get all expenses by user
    public List<ExpenseDTO> getAllExpensesByUser(Integer userId) {
        return expenseRepository.findByUserUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Update Expense
    public ExpenseDTO updateExpense(Integer id, ExpenseDTO dto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setDescription(dto.getDescription());
        expense.setPaymentMethod(dto.getPaymentMethod());

        Expense updated = expenseRepository.save(expense);
        return convertToDTO(updated);
    }

    // ✅ Delete Expense
    public void deleteExpense(Integer id) {
        expenseRepository.deleteById(id);
    }

    // ✅ Deduct from Budget
    private void deductFromBudget(Integer userId, Integer categoryId, BigDecimal amount, LocalDate expenseDate) {
        Budget budget = (Budget) budgetRepository
                .findByUserUserIdAndCategoryCategoryIdAndStartDate(userId, categoryId, expenseDate.withDayOfMonth(1))
                .orElse(null);

        if (budget != null) {
            BigDecimal newAmount = budget.getBudgetAmount().subtract(amount);
            budget.setBudgetAmount(newAmount.max(BigDecimal.ZERO));
            budgetRepository.save(budget);
        }
    }

    // ✅ Convert Entity → DTO
    private ExpenseDTO convertToDTO(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setExpenseId(expense.getExpenseId());
        dto.setUserId(expense.getUser().getUserId());
        dto.setCategoryId(expense.getCategory().getCategoryId());
        dto.setAmount(expense.getAmount());
        dto.setExpenseDate(expense.getExpenseDate());
        dto.setDescription(expense.getDescription());
        dto.setPaymentMethod(expense.getPaymentMethod());
        return dto;
    }
}
