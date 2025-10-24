package com.financeapp.personal_finance_manager.entity;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserId(Long userId);

    List<Income> findByUserIdOrderByIncomeDateDesc(Long userId);

    List<Income> findByUserIdAndIncomeDateBetween(
            Long userId, LocalDate startDate, LocalDate endDate
    );

    List<Income> findByUserIdAndIsSynced(Long userId, Boolean isSynced);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.userId = :userId " +
            "AND i.incomeDate BETWEEN :startDate AND :endDate")
    BigDecimal sumIncomeByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
