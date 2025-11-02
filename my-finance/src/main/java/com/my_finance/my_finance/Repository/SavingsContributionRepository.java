package com.my_finance.my_finance.Repository;

import com.my_finance.my_finance.Entity.SavingsContribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsContributionRepository extends JpaRepository<SavingsContribution, Integer> {
    List<SavingsContribution> findByUserUserId(Integer userId);
    List<SavingsContribution> findBySavingsGoalGoalId(Integer goalId);
}
