package com.my_finance.my_finance.Repository;

import com.my_finance.my_finance.Entity.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Integer> {
    List<SavingsGoal> findByUserUserId(Integer userId);
}

