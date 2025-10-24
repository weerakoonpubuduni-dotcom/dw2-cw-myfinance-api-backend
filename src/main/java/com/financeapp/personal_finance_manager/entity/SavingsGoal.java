package com.financeapp.personal_finance_manager.entity;


@Entity
@Table(name = "savings_goals")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "goal_name", nullable = false, length = 100)
    private String goalName;

    @Column(name = "target_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal targetAmount;

    @Column(name = "current_amount", precision = 10, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_status", length = 20)
    private GoalStatus goalStatus = GoalStatus.ACTIVE;

    @Column(nullable = false)
    private Integer priority = 3;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_synced", nullable = false)
    private Boolean isSynced = false;

    public enum GoalStatus {
        ACTIVE, COMPLETED, CANCELLED
    }
}
