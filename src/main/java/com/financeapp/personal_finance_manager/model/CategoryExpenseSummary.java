package com.financeapp.personal_finance_manager.model;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryExpenseSummary {
    private Long categoryId;
    private String categoryName;
    private BigDecimal totalAmount;
    private Integer transactionCount;
    private Double percentage;
}
