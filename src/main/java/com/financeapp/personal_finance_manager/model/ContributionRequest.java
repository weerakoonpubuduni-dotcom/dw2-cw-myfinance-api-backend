package com.financeapp.personal_finance_manager.model;

@Data
public class ContributionRequest {
    @NotNull(message = "Goal ID is required")
    private Long goalId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Contribution date is required")
    private LocalDate contributionDate;

    private String notes;
}
