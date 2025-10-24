package com.financeapp.personal_finance_manager.entity;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserId(Long userId);

    List<Budget> findByUserIdOrderByStartDateDesc(Long userId);

    List<Budget> findByUserIdAndBudgetPeriod(Long userId, Budget.BudgetPeriod budgetPeriod);

    @Query("SELECT b FROM Budget b WHERE b.userId = :userId " +
            "AND b.startDate <= :currentDate AND b.endDate >= :currentDate")
    List<Budget> findActiveBudgets(
            @Param("userId") Long userId,
            @Param("currentDate") LocalDate currentDate
    );

    Optional<Budget> findByUserIdAndCategoryIdAndStartDate(
            Long userId, Long categoryId, LocalDate startDate
    );

    List<Budget> findByUserIdAndIsSynced(Long userId, Boolean isSynced);

    @Query("SELECT b FROM Budget b WHERE b.userId = :userId " +
            "AND :date BETWEEN b.startDate AND b.endDate")
    List<Budget> findBudgetsForDate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );
}