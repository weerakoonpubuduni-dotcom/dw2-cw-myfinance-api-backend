package com.financeapp.personal_finance_manager.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SavingsContributionRepository extends JpaRepository<SavingsContribution, Long> {

    List<SavingsContribution> findByGoalId(Long goalId);

    List<SavingsContribution> findByGoalIdOrderByContributionDateDesc(Long goalId);

    List<SavingsContribution> findByUserIdAndIsSynced(Long userId, Boolean isSynced);

    @Query("SELECT SUM(sc.amount) FROM SavingsContribution sc " +
            "WHERE sc.goalId = :goalId")
    BigDecimal sumContributionsByGoalId(@Param("goalId") Long goalId);

    @Query("SELECT SUM(sc.amount) FROM SavingsContribution sc " +
            "WHERE sc.goalId = :goalId " +
            "AND sc.contributionDate BETWEEN :startDate AND :endDate")
    BigDecimal sumContributionsByGoalIdAndDateRange(
            @Param("goalId") Long goalId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
