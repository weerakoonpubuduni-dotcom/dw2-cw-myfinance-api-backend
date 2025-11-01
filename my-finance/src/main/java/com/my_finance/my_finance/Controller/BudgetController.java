package com.my_finance.my_finance.Controller;

import com.my_finance.my_finance.DTO.BudgetDTO;
import com.my_finance.my_finance.Entity.Budget;
import com.my_finance.my_finance.Service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/budgets")
//@CrossOrigin
public class BudgetController {
    @Autowired  private BudgetService budgetService;

    @GetMapping("/user/{userId}")
    public List<BudgetDTO> getBudgetsByUser(@PathVariable Integer userId) {
        return budgetService.getBudgetsByUser(userId);
    }

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(@RequestBody BudgetDTO dto) {
        return ResponseEntity.ok(budgetService.createBudget(dto));
    }

    // Update existing budget
    @PutMapping("/{budgetId}")
    public Budget updateBudget(@PathVariable Integer budgetId, @RequestBody Budget updatedBudget) {
        return budgetService.updateBudget(budgetId, updatedBudget);
    }

    // Delete a budget
    @DeleteMapping("/{budgetId}")
    public String deleteBudget(@PathVariable Integer budgetId) {
        budgetService.deleteBudget(budgetId);
        return "Budget with ID " + budgetId + " has been deleted successfully.";
    }



}
