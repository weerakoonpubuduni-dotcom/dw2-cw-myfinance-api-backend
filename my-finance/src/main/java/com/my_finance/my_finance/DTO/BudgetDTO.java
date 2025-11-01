package com.my_finance.my_finance.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BudgetDTO {
    private Integer budgetId;
    private Integer userId;
    private Integer categoryId;
    private BigDecimal budgetAmount;
    private String budgetPeriod;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal alertThreshold;

    // Getters & Setters
    public Integer getBudgetId() { return budgetId; }
    public void setBudgetId(Integer budgetId) { this.budgetId = budgetId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public BigDecimal getBudgetAmount() { return budgetAmount; }
    public void setBudgetAmount(BigDecimal budgetAmount) { this.budgetAmount = budgetAmount; }

    public String getBudgetPeriod() { return budgetPeriod; }
    public void setBudgetPeriod(String budgetPeriod) { this.budgetPeriod = budgetPeriod; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public BigDecimal getAlertThreshold() { return alertThreshold; }
    public void setAlertThreshold(BigDecimal alertThreshold) { this.alertThreshold = alertThreshold; }
}
