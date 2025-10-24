package com.financeapp.personal_finance_manager.entity;

@Repository
public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {

    List<SavingsGoal> findByUserId(Long userId);

    List<SavingsGoal> findByUserIdAndGoalStatus(
            Long userId, SavingsGoal.GoalStatus goalStatus
    );

    List<SavingsGoal> findByUserIdOrderByPriorityAscTargetDateAsc(Long userId);

    List<SavingsGoal> findByUserIdAndIsSynced(Long userId, Boolean isSynced);

    @Query("SELECT SUM(sg.currentAmount) FROM SavingsGoal sg " +
            "WHERE sg.userId = :userId AND sg.goalStatus = :status")
    BigDecimal sumCurrentAmountByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") SavingsGoal.GoalStatus status
    );
}
