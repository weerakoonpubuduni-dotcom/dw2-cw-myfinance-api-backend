package com.my_finance.my_finance.DTO;
import java.time.LocalDate;

public class ExpenseDTO {

    private Integer expenseId;
    private Integer userId;
    private Integer categoryId;
    private Double amount;
    private LocalDate expenseDate;
    private String description;
    private String paymentMethod;

    // Getters & Setters
    public Integer getExpenseId() { return expenseId; }
    public void setExpenseId(Integer expenseId) { this.expenseId = expenseId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
