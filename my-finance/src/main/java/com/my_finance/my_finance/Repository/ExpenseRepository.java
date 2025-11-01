package com.my_finance.my_finance.Repository;
import com.my_finance.my_finance.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ExpenseRepository extends JpaRepository<Expense,Integer> {
    List<Expense> findByUserUserId(Integer userId);// Find all expenses that belong to the user with the given userId
    Optional<Expense> findByUserUserIdAndCategoryCategoryIdAndStartDate(Integer userId, Integer categoryId, LocalDate startDate);
}
