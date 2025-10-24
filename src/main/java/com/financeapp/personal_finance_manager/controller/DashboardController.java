package com.financeapp.personal_finance_manager.controller;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    private Long getCurrentUserId() {
        return 1L; // Placeholder
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestParam(required = false, defaultValue = "30") Integer days) {

        Long userId = getCurrentUserId();
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);

        // Calculate totals
        BigDecimal totalIncome = incomeRepository.sumIncomeByUserIdAndDateRange(userId, startDate, endDate);
        BigDecimal totalExpenses = expenseRepository.sumExpensesByUserIdAndDateRange(userId, startDate, endDate);

        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;

        BigDecimal netSavings = totalIncome.subtract(totalExpenses);

        // Get category breakdown
        List<Object[]> categoryData = expenseRepository.findExpenseSummaryByCategory(userId, startDate, endDate);
        List<CategoryExpenseSummary> topExpenseCategories = categoryData.stream()
                .limit(5)
                .map(data -> CategoryExpenseSummary.builder()
                        .categoryId((Long) data[0])
                        .categoryName((String) data[1])
                        .totalAmount((BigDecimal) data[2])
                        .build())
                .collect(Collectors.toList());

        // Count active items
        Integer activeBudgetsCount = budgetRepository.findActiveBudgets(userId, LocalDate.now()).size();
        Integer activeSavingsGoalsCount = savingsGoalRepository
                .findByUserIdAndGoalStatus(userId, SavingsGoal.GoalStatus.ACTIVE).size();

        DashboardResponse dashboard = DashboardResponse.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netSavings(netSavings)
                .activeBudgetsCount(activeBudgetsCount)
                .activeSavingsGoalsCount(activeSavingsGoalsCount)
                .topExpenseCategories(topExpenseCategories)
                .build();

        return ResponseEntity.ok(dashboard);
    }
}
