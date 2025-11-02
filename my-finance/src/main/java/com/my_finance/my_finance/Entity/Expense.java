package com.my_finance.my_finance.Entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="expense_id")
    private Integer expenseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name="amount",nullable = false)
    private Double amount;

    @Column(name="expense_date",nullable = false)
    private LocalDate expenseDate;

    @Column(name="description")
    private String description;

    @Column(name="payment_method")
    private String paymentMethod;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="is_synced")
    private Integer isSynced;

    @Column(name="sync_timestamp")
    private LocalDateTime syncTimestamp;

    // Getters & Setters
    public Integer getExpenseId() { return expenseId; }
    public void setExpenseId(Integer expenseId) { this.expenseId = expenseId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Integer getIsSynced() { return isSynced; }
    public void setIsSynced(Integer isSynced) { this.isSynced = isSynced; }

    public LocalDateTime getSyncTimestamp() { return syncTimestamp; }
    public void setSyncTimestamp(LocalDateTime syncTimestamp) { this.syncTimestamp = syncTimestamp; }
}
