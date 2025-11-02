package com.my_finance.my_finance.Controller;

import com.my_finance.my_finance.DTO.ExpenseDTO;
import com.my_finance.my_finance.Service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/expenses")
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ExpenseController {
    @Autowired private ExpenseService expenseService;

    // Create a new expense
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO createdExpense = expenseService.createExpense(expenseDTO);
        return ResponseEntity.ok(createdExpense);
    }

    // Get all expenses by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUser(@PathVariable Integer userId) {
        List<ExpenseDTO> expenses = expenseService.getAllExpensesByUser(userId);
        return ResponseEntity.ok(expenses);
    }

    // Update an existing expense
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(
            @PathVariable Integer id,
            @RequestBody ExpenseDTO expenseDTO
    ) {
        ExpenseDTO updatedExpense = expenseService.updateExpense(id, expenseDTO);
        return ResponseEntity.ok(updatedExpense);
    }

    // Delete an expense
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Integer id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }

}
