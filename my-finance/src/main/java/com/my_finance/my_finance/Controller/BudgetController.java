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
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetController {
    @Autowired  private BudgetService budgetService;



    @GetMapping("/{userId}")
    public List<BudgetDTO> getBudgetsByUser(@PathVariable Integer userId) {
        return budgetService.getBudgetsByUser(userId);
    }

    @PostMapping
    public ResponseEntity<BudgetDTO> createBudget(@RequestBody BudgetDTO dto) {
        return ResponseEntity.ok(budgetService.createBudget(dto));
    }

    // Update existing budget
    @PutMapping("/{budgetId}")
    public ResponseEntity<BudgetDTO> updateBudget(@PathVariable Integer budgetId, @RequestBody BudgetDTO dto) {
        BudgetDTO updated = budgetService.updateBudget(budgetId, dto);
        return ResponseEntity.ok(updated);
    }


    // Delete a budget
    @DeleteMapping("/{budgetId}")
    public String deleteBudget(@PathVariable Integer budgetId) {
        budgetService.deleteBudget(budgetId);
        return "Budget with ID " + budgetId + " has been deleted successfully.";
    }
}
