package com.financeapp.personal_finance_manager.service;

import com.finance.dto.request.IncomeRequest;
import com.finance.dto.response.MessageResponse;
import com.finance.model.Income;
import com.finance.repository.local.IncomeRepository;
import com.finance.repository.local.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Income> getAllIncome(Long userId, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return incomeRepository.findByUserIdAndIncomeDateBetween(userId, startDate, endDate);
        }
        return incomeRepository.findByUserIdOrderByIncomeDateDesc(userId);
    }

    public Income getIncomeById(Long incomeId, Long userId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found"));

        if (!income.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return income;
    }

    @Transactional
    public Income createIncome(IncomeRequest request, Long userId) {
        // Verify category belongs to user
        categoryRepository.findById(request.getCategoryId())
                .filter(c -> c.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Income income = new Income();
        income.setUserId(userId);
        income.setCategoryId(request.getCategoryId());
        income.setAmount(request.getAmount());
        income.setIncomeDate(request.getIncomeDate());
        income.setDescription(request.getDescription());
        income.setSource(request.getSource());
        income.setIsSynced(false);

        return incomeRepository.save(income);
    }

    @Transactional
    public Income updateIncome(Long incomeId, IncomeRequest request, Long userId) {
        Income income = getIncomeById(incomeId, userId);

        income.setAmount(request.getAmount());
        income.setIncomeDate(request.getIncomeDate());
        income.setDescription(request.getDescription());
        income.setSource(request.getSource());
        income.setIsSynced(false);

        return incomeRepository.save(income);
    }

    @Transactional
    public void deleteIncome(Long incomeId, Long userId) {
        Income income = getIncomeById(incomeId, userId);
        incomeRepository.delete(income);
    }
}

