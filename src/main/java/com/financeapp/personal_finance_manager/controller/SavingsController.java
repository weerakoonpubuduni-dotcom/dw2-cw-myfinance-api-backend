package com.financeapp.personal_finance_manager.controller;

import com.financeapp.personal_finance_manager.model.ContributionRequest;
import com.financeapp.personal_finance_manager.model.MessageResponse;
import com.financeapp.personal_finance_manager.model.SavingsGoalRequest;
import com.financeapp.personal_finance_manager.model.SavingsGoalResponse;
import com.financeapp.personal_finance_manager.service.SavingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SavingsController {

    @Autowired
    private SavingsService savingsService;

    private Long getCurrentUserId() {
        return 1L; // Placeholder
    }

    @GetMapping("/goals")
    public ResponseEntity<List<SavingsGoalResponse>> getAllGoals() {
        Long userId = getCurrentUserId();
        List<SavingsGoalResponse> goals = savingsService.getAllGoals(userId);
        return ResponseEntity.ok(goals);
    }

    @GetMapping("/goals/active")
    public ResponseEntity<List<SavingsGoalResponse>> getActiveGoals() {
        Long userId = getCurrentUserId();
        List<SavingsGoalResponse> goals = savingsService.getActiveGoals(userId);
        return ResponseEntity.ok(goals);
    }

    @GetMapping("/goals/{id}")
    public ResponseEntity<SavingsGoalResponse> getGoalById(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        SavingsGoalResponse goal = savingsService.getGoalById(id, userId);
        return ResponseEntity.ok(goal);
    }

    @PostMapping("/goals")
    public ResponseEntity<SavingsGoalResponse> createGoal(@Valid @RequestBody SavingsGoalRequest request) {
        Long userId = getCurrentUserId();
        SavingsGoalResponse goal = savingsService.createGoal(request, userId);
        return ResponseEntity.ok(goal);
    }

    @PutMapping("/goals/{id}")
    public ResponseEntity<SavingsGoalResponse> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody SavingsGoalRequest request) {
        Long userId = getCurrentUserId();
        SavingsGoalResponse goal = savingsService.updateGoal(id, request, userId);
        return ResponseEntity.ok(goal);
    }

    @DeleteMapping("/goals/{id}")
    public ResponseEntity<MessageResponse> deleteGoal(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        savingsService.deleteGoal(id, userId);
        return ResponseEntity.ok(new MessageResponse("Goal deleted successfully"));
    }

    @PostMapping("/contributions")
    public ResponseEntity<SavingsGoalResponse> addContribution(@Valid @RequestBody ContributionRequest request) {
        Long userId = getCurrentUserId();
        SavingsGoalResponse goal = savingsService.addContribution(request, userId);
        return ResponseEntity.ok(goal);
    }
}
