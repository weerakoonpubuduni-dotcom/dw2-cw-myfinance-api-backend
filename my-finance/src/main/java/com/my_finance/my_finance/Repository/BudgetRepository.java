package com.my_finance.my_finance.Repository;

import com.my_finance.my_finance.Entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository  extends JpaRepository<Budget,Integer>{
    List<Budget> findByUserUserId(Integer userId);

    @Query("SELECT b FROM Budget b WHERE b.user.userId = :userId AND b.category.categoryId = :categoryId AND :expenseDate BETWEEN b.startDate AND b.endDate")
    Budget findActiveBudget(Integer userId, Integer categoryId, LocalDate expenseDate);

    Optional<Budget> findByUserUserIdAndCategoryCategoryIdAndStartDate(Integer userId, Integer categoryId, LocalDate localDate);
}
