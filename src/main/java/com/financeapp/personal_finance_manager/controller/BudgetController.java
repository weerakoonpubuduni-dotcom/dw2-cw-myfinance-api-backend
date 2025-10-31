package com.financeapp.personal_finance_manager.controller;

import com.financeapp.personal_finance_manager.model.BudgetRequest;
import com.financeapp.personal_finance_manager.model.BudgetResponse;
import com.financeapp.personal_finance_manager.model.MessageResponse;
import com.financeapp.personal_finance_manager.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    private Long getCurrentUserId() {
        return 1L; // Placeholder
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAllBudgets() {
        Long userId = getCurrentUserId();
        List<BudgetResponse> budgets = budgetService.getAllBudgets(userId);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/active")
    public ResponseEntity<List<BudgetResponse>> getActiveBudgets() {
        Long userId = getCurrentUserId();
        List<BudgetResponse> budgets = budgetService.getActiveBudgets(userId);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudgetById(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        BudgetResponse budget = budgetService.getBudgetById(id, userId);
        return ResponseEntity.ok(budget);
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@Valid @RequestBody BudgetRequest request) {
        Long userId = getCurrentUserId();
        BudgetResponse budget = budgetService.createBudget(request, userId);
        return ResponseEntity.ok(budget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest request) {
        Long userId = getCurrentUserId();
        BudgetResponse budget = budgetService.updateBudget(id, request, userId);
        return ResponseEntity.ok(budget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteBudget(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        budgetService.deleteBudget(id, userId);
        return ResponseEntity.ok(new MessageResponse("Budget deleted successfully"));
    }
}

