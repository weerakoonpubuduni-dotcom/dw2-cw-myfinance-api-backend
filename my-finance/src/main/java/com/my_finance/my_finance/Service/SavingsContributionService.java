package com.my_finance.my_finance.Service;

import com.my_finance.my_finance.DTO.SavingsContributionDTO;
import com.my_finance.my_finance.Entity.SavingsContribution;
import com.my_finance.my_finance.Entity.SavingsGoal;
import com.my_finance.my_finance.Entity.User;
import com.my_finance.my_finance.Repository.SavingsContributionRepository;
import com.my_finance.my_finance.Repository.SavingsGoalRepository;
import com.my_finance.my_finance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsContributionService {
    @Autowired private SavingsContributionRepository contributionRepository;
    @Autowired private SavingsGoalRepository goalRepository;
    @Autowired private UserRepository userRepository;

    //  Create / Add Contribution
    public SavingsContributionDTO addContribution(SavingsContributionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        SavingsGoal goal = goalRepository.findById(dto.getGoalId())
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        SavingsContribution contribution = new SavingsContribution();
        contribution.setUser(user);
        contribution.setSavingsGoal(goal);
        contribution.setAmount(dto.getAmount());
        contribution.setContributionDate(dto.getContributionDate());
        contribution.setNotes(dto.getNotes());
        contribution.setCreatedAt(LocalDateTime.now());
        contribution.setIsSynced(0);

        // Update goal’s current amount
        goal.setCurrentAmount(goal.getCurrentAmount().add(dto.getAmount()));
        goalRepository.save(goal);

        return convertToDTO(contributionRepository.save(contribution));
    }

    // Get all contributions by goal ID
    public List<SavingsContributionDTO> getAllByGoal(Integer goalId) {
        return contributionRepository.findBySavingsGoalGoalId(goalId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get all contributions by user ID
    public List<SavingsContributionDTO> getAllByUser(Integer userId) {
        return contributionRepository.findByUserUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Update a contribution
    public SavingsContributionDTO updateContribution(Integer id, SavingsContributionDTO dto) {
        SavingsContribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contribution not found"));

        // Adjust the goal’s amount difference
        SavingsGoal goal = contribution.getSavingsGoal();
        goal.setCurrentAmount(goal.getCurrentAmount()
                .subtract(contribution.getAmount())
                .add(dto.getAmount()));
        goalRepository.save(goal);

        // Update contribution fields
        contribution.setAmount(dto.getAmount());
        contribution.setContributionDate(dto.getContributionDate());
        contribution.setNotes(dto.getNotes());
        contribution.setIsSynced(0);

        SavingsContribution updated = contributionRepository.save(contribution);
        return convertToDTO(updated);
    }

    //Delete a contribution
    public void deleteContribution(Integer id) {
        SavingsContribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contribution not found"));

        // Reverse the amount from goal’s current total
        SavingsGoal goal = contribution.getSavingsGoal();
        goal.setCurrentAmount(goal.getCurrentAmount().subtract(contribution.getAmount()));
        goalRepository.save(goal);

        contributionRepository.deleteById(id);
    }

    //Convert Entity → DTO
    private SavingsContributionDTO convertToDTO(SavingsContribution c) {
        SavingsContributionDTO dto = new SavingsContributionDTO();
        dto.setContributionId(c.getContributionId());
        dto.setGoalId(c.getSavingsGoal().getGoalId());
        dto.setUserId(c.getUser().getUserId());
        dto.setAmount(c.getAmount());
        dto.setContributionDate(c.getContributionDate());
        dto.setNotes(c.getNotes());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setIsSynced(c.getIsSynced());
        return dto;
    }
}
