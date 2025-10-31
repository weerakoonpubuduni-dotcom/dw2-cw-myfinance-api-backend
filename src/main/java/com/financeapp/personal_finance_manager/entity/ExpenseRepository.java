package com.financeapp.personal_finance_manager.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);

    List<Expense> findByUserIdOrderByExpenseDateDesc(Long userId);

    List<Expense> findByUserIdAndExpenseDateBetween(
            Long userId, LocalDate startDate, LocalDate endDate
    );

    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);

    List<Expense> findByUserIdAndIsSynced(Long userId, Boolean isSynced);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal sumExpensesByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId " +
            "AND e.categoryId = :categoryId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal sumExpensesByCategoryAndDateRange(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT e.categoryId, c.categoryName, SUM(e.amount) as total " +
            "FROM Expense e JOIN e.category c " +
            "WHERE e.userId = :userId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY e.categoryId, c.categoryName " +
            "ORDER BY total DESC")
    List<Object[]> findExpenseSummaryByCategory(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}