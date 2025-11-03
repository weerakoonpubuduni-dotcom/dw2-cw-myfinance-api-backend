package com.my_finance.my_finance.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "savings_goals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goalId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="goal_name")
    private String goalName;

    @Column(name="target_amount")
    private BigDecimal targetAmount;

    @Column(name="current_amount")
    private BigDecimal currentAmount;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="target_date")
    private LocalDate targetDate;

    @Column(name="goal_status")
    private String goalStatus;

    @Column(name="priority")
    private Integer priority;

    @Column(name="description")
    private String description;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="is_synced")
    private Integer isSynced;

    // Getters and Setters
    public Integer getGoalId() { return goalId; }
    public void setGoalId(Integer goalId) { this.goalId = goalId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getGoalName() { return goalName; }
    public void setGoalName(String goalName) { this.goalName = goalName; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public String getGoalStatus() { return goalStatus; }
    public void setGoalStatus(String goalStatus) { this.goalStatus = goalStatus; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Integer getIsSynced() { return isSynced; }
    public void setIsSynced(Integer isSynced) { this.isSynced = isSynced; }


}
