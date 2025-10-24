package com.financeapp.personal_finance_manager.model;

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
