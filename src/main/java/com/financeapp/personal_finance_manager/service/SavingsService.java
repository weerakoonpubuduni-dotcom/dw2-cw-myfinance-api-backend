package com.financeapp.personal_finance_manager.service;

import com.financeapp.personal_finance_manager.entity.SavingsContribution;
import com.financeapp.personal_finance_manager.entity.SavingsContributionRepository;
import com.financeapp.personal_finance_manager.entity.SavingsGoal;
import com.financeapp.personal_finance_manager.entity.SavingsGoalRepository;
import com.financeapp.personal_finance_manager.model.ContributionRequest;
import com.financeapp.personal_finance_manager.model.SavingsGoalRequest;
import com.financeapp.personal_finance_manager.model.SavingsGoalResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsService {

    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    @Autowired
    private SavingsContributionRepository contributionRepository;

    public List<SavingsGoalResponse> getAllGoals(Long userId) {
        List<SavingsGoal> goals = savingsGoalRepository
                .findByUserIdOrderByPriorityAscTargetDateAsc(userId);
        return goals.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SavingsGoalResponse> getActiveGoals(Long userId) {
        List<SavingsGoal> goals = savingsGoalRepository
                .findByUserIdAndGoalStatus(userId, SavingsGoal.GoalStatus.ACTIVE);
        return goals.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SavingsGoalResponse getGoalById(Long goalId, Long userId) {
        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Savings goal not found"));

        if (!goal.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return mapToResponse(goal);
    }

    @Transactional
    public SavingsGoalResponse createGoal(SavingsGoalRequest request, Long userId) {
        SavingsGoal goal = new SavingsGoal();
        goal.setUserId(userId);
        goal.setGoalName(request.getGoalName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(BigDecimal.ZERO);
        goal.setStartDate(request.getStartDate());
        goal.setTargetDate(request.getTargetDate());
        goal.setPriority(request.getPriority() != null ? request.getPriority() : 3);
        goal.setDescription(request.getDescription());
        goal.setGoalStatus(SavingsGoal.GoalStatus.ACTIVE);
        goal.setIsSynced(false);

        SavingsGoal savedGoal = savingsGoalRepository.save(goal);
        return mapToResponse(savedGoal);
    }

    @Transactional
    public SavingsGoalResponse updateGoal(Long goalId, SavingsGoalRequest request, Long userId) {
        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Savings goal not found"));

        if (!goal.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        goal.setGoalName(request.getGoalName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setTargetDate(request.getTargetDate());
        goal.setPriority(request.getPriority());
        goal.setDescription(request.getDescription());
        goal.setIsSynced(false);

        SavingsGoal updatedGoal = savingsGoalRepository.save(goal);
        return mapToResponse(updatedGoal);
    }

    @Transactional
    public void deleteGoal(Long goalId, Long userId) {
        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Savings goal not found"));

        if (!goal.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        savingsGoalRepository.delete(goal);
    }

    @Transactional
    public SavingsGoalResponse addContribution(ContributionRequest request, Long userId) {
        SavingsGoal goal = savingsGoalRepository.findById(request.getGoalId())
                .orElseThrow(() -> new RuntimeException("Savings goal not found"));

        if (!goal.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        // Create contribution
        SavingsContribution contribution = new SavingsContribution();
        contribution.setGoalId(request.getGoalId());
        contribution.setUserId(userId);
        contribution.setAmount(request.getAmount());
        contribution.setContributionDate(request.getContributionDate());
        contribution.setNotes(request.getNotes());
        contribution.setIsSynced(false);

        contributionRepository.save(contribution);

        // Update goal
        goal.setCurrentAmount(goal.getCurrentAmount().add(request.getAmount()));
        if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setGoalStatus(SavingsGoal.GoalStatus.COMPLETED);
        }
        goal.setIsSynced(false);

        SavingsGoal updatedGoal = savingsGoalRepository.save(goal);
        return mapToResponse(updatedGoal);
    }

    private SavingsGoalResponse mapToResponse(SavingsGoal goal) {
        BigDecimal remainingAmount = goal.getTargetAmount().subtract(goal.getCurrentAmount());
        Double progressPercentage = goal.getCurrentAmount()
                .divide(goal.getTargetAmount(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .doubleValue();

        Long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), goal.getTargetDate());

        List<SavingsContribution> contributions = contributionRepository.findByGoalId(goal.getGoalId());

        return SavingsGoalResponse.builder()
                .goalId(goal.getGoalId())
                .goalName(goal.getGoalName())
                .targetAmount(goal.getTargetAmount())
                .currentAmount(goal.getCurrentAmount())
                .remainingAmount(remainingAmount)
                .progressPercentage(progressPercentage)
                .startDate(goal.getStartDate())
                .targetDate(goal.getTargetDate())
                .daysRemaining(daysRemaining)
                .goalStatus(goal.getGoalStatus())
                .priority(goal.getPriority())
                .description(goal.getDescription())
                .contributionCount(contributions.size())
                .build();
    }
}