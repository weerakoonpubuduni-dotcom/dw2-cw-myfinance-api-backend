package com.my_finance.my_finance.Service;

import com.my_finance.my_finance.DTO.BudgetDTO;
import com.my_finance.my_finance.Entity.Budget;
import com.my_finance.my_finance.Entity.Category;
import com.my_finance.my_finance.Entity.User;
import com.my_finance.my_finance.Repository.BudgetRepository;
import com.my_finance.my_finance.Repository.CategoryRepository;
import com.my_finance.my_finance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {
    @Autowired private BudgetRepository budgetRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;

    public List<BudgetDTO> getBudgetsByUser(Integer userId) {
        return budgetRepository.findByUserUserId(userId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BudgetDTO createBudget(BudgetDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(category);
        budget.setBudgetAmount(dto.getBudgetAmount());
        budget.setBudgetPeriod(dto.getBudgetPeriod());
        budget.setStartDate(dto.getStartDate());
        budget.setEndDate(dto.getEndDate());
        budget.setAlertThreshold(dto.getAlertThreshold());

        Budget saved = budgetRepository.save(budget);
        return convertToDTO(saved);
    }

    private BudgetDTO convertToDTO(Budget b) {
        BudgetDTO dto = new BudgetDTO();
        dto.setBudgetId(b.getBudgetId());
        dto.setUserId(b.getUser().getUserId());
        dto.setCategoryId(b.getCategory().getCategoryId());
        dto.setBudgetAmount(b.getBudgetAmount());
        dto.setBudgetPeriod(b.getBudgetPeriod());
        dto.setStartDate(b.getStartDate());
        dto.setEndDate(b.getEndDate());
        dto.setAlertThreshold(b.getAlertThreshold());
        return dto;
    }

    public BudgetDTO updateBudget(Integer id, BudgetDTO dto) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        // Keep existing user and category
        budget.setBudgetAmount(dto.getBudgetAmount());
        budget.setBudgetPeriod(dto.getBudgetPeriod());
        budget.setStartDate(dto.getStartDate());
        budget.setEndDate(dto.getEndDate());
        budget.setAlertThreshold(dto.getAlertThreshold());
        budget.setUpdatedAt(LocalDateTime.now());

        Budget saved = budgetRepository.save(budget);
        return convertToDTO(saved);
    }


    public void deleteBudget(Integer id) {
        budgetRepository.deleteById(id);
    }
}
