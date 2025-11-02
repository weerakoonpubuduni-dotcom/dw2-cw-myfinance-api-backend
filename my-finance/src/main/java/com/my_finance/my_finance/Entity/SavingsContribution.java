package com.my_finance.my_finance.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "savings_contributions")
public class SavingsContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contributionId;

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private SavingsGoal savingsGoal;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="contribution_date")
    private LocalDate contributionDate;

    @Column(name="notes")
    private String notes;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="is_synced")
    private Integer isSynced;

    // Getters and Setters
    public Integer getContributionId() { return contributionId; }
    public void setContributionId(Integer contributionId) { this.contributionId = contributionId; }

    public SavingsGoal getSavingsGoal() { return savingsGoal; }
    public void setSavingsGoal(SavingsGoal savingsGoal) { this.savingsGoal = savingsGoal; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getContributionDate() { return contributionDate; }
    public void setContributionDate(LocalDate contributionDate) { this.contributionDate = contributionDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getIsSynced() { return isSynced; }
    public void setIsSynced(Integer isSynced) { this.isSynced = isSynced; }

}
