package com.financeapp.personal_finance_manager.model;

import com.financeapp.personal_finance_manager.entity.SavingsGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsGoalResponse {
    private Long goalId;
    private String goalName;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private BigDecimal remainingAmount;
    private Double progressPercentage;
    private LocalDate startDate;
    private LocalDate targetDate;
    private Long daysRemaining;
    private SavingsGoal.GoalStatus goalStatus;
    private Integer priority;
    private String description;
    private Integer contributionCount;
}
