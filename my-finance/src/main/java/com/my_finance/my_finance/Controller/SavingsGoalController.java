package com.my_finance.my_finance.Controller;


import com.my_finance.my_finance.DTO.SavingsGoalDTO;
import com.my_finance.my_finance.Service.SavingsGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-goals")
//@CrossOrigin(origins = "*")
public class SavingsGoalController {

    @Autowired
    private SavingsGoalService goalService;

    // Create a new savings goal
    @PostMapping
    public ResponseEntity<SavingsGoalDTO> createGoal(@RequestBody SavingsGoalDTO dto) {
        return ResponseEntity.ok(goalService.createGoal(dto));
    }

    // Get all goals by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavingsGoalDTO>> getGoalsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(goalService.getAllGoalsByUser(userId));
    }

    // Update an existing savings goal
    @PutMapping("/{goalId}")
    public ResponseEntity<SavingsGoalDTO> updateGoal(
            @PathVariable Integer goalId,
            @RequestBody SavingsGoalDTO dto) {
        SavingsGoalDTO updatedGoal = goalService.updateGoal(goalId, dto);
        return ResponseEntity.ok(updatedGoal);
    }

    // Delete a savings goal
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable Integer id) {
        goalService.deleteGoal(id);
        return ResponseEntity.ok("Goal deleted successfully");
    }
}
