package com.my_finance.my_finance.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "savings_goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goalId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="goal_name",nullable = false)
    private String goalName;

    private BigDecimal targetAmount;
    private BigDecimal currentAmount;

    private LocalDate startDate;
    private LocalDate targetDate;

    private String goalStatus;
    private Integer priority;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer isSynced;
}
