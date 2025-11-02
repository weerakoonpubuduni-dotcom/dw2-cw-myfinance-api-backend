package com.my_finance.my_finance.Service;

import com.my_finance.my_finance.DTO.SavingsGoalDTO;
import com.my_finance.my_finance.Entity.SavingsGoal;
import com.my_finance.my_finance.Entity.User;
import com.my_finance.my_finance.Repository.SavingsGoalRepository;
import com.my_finance.my_finance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsGoalService {
    @Autowired private SavingsGoalRepository savingsGoalRepository;
    @Autowired private UserRepository userRepository;

    public SavingsGoalDTO createGoal(SavingsGoalDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SavingsGoal goal = new SavingsGoal();
        goal.setUser(user);
        goal.setGoalName(dto.getGoalName());
        goal.setTargetAmount(dto.getTargetAmount());
        goal.setCurrentAmount(dto.getCurrentAmount());
        goal.setStartDate(dto.getStartDate());
        goal.setTargetDate(dto.getTargetDate());
        goal.setGoalStatus(dto.getGoalStatus());
        goal.setPriority(dto.getPriority());
        goal.setDescription(dto.getDescription());
        goal.setCreatedAt(LocalDateTime.now());
        goal.setUpdatedAt(LocalDateTime.now());
        goal.setIsSynced(0);

        return convertToDTO(savingsGoalRepository.save(goal));
    }

    public List<SavingsGoalDTO> getAllGoalsByUser(Integer userId) {
        return savingsGoalRepository.findByUserUserId(userId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public SavingsGoalDTO updateGoal(Integer goalId, SavingsGoalDTO dto) {
        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Savings goal not found"));

        // Update fields
        goal.setGoalName(dto.getGoalName());
        goal.setTargetAmount(dto.getTargetAmount());
        goal.setCurrentAmount(dto.getCurrentAmount());
        goal.setStartDate(dto.getStartDate());
        goal.setTargetDate(dto.getTargetDate());
        goal.setGoalStatus(dto.getGoalStatus());
        goal.setPriority(dto.getPriority());
        goal.setDescription(dto.getDescription());
        goal.setUpdatedAt(LocalDateTime.now()); // automatically set updated time
        goal.setIsSynced(0); // mark as not yet synced

        // Save updated goal
        SavingsGoal updatedGoal = savingsGoalRepository.save(goal);
        return convertToDTO(updatedGoal);
    }

    public void deleteGoal(Integer id) { savingsGoalRepository.deleteById(id); }

    private SavingsGoalDTO convertToDTO(SavingsGoal goal) {
        SavingsGoalDTO dto = new SavingsGoalDTO();
        dto.setGoalId(goal.getGoalId());
        dto.setUserId(goal.getUser().getUserId());
        dto.setGoalName(goal.getGoalName());
        dto.setTargetAmount(goal.getTargetAmount());
        dto.setCurrentAmount(goal.getCurrentAmount());
        dto.setStartDate(goal.getStartDate());
        dto.setTargetDate(goal.getTargetDate());
        dto.setGoalStatus(goal.getGoalStatus());
        dto.setPriority(goal.getPriority());
        dto.setDescription(goal.getDescription());
        dto.setUpdatedAt(goal.getUpdatedAt());
        dto.setCreatedAt(goal.getCreatedAt());   // map createdAt
        dto.setIsSynced(goal.getIsSynced());     //  map isSynced
        return dto;
    }
}
