package com.my_finance.my_finance.Controller;


import com.my_finance.my_finance.DTO.SavingsContributionDTO;
import com.my_finance.my_finance.Service.SavingsContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-contributions")
//@CrossOrigin(origins = "*")
public class SavingsContributionController {

    @Autowired private SavingsContributionService contributionService;

    // Create Contribution
    @PostMapping
    public ResponseEntity<SavingsContributionDTO> addContribution(@RequestBody SavingsContributionDTO dto) {
        return ResponseEntity.ok(contributionService.addContribution(dto));
    }

    //  Get Contributions by Goal
    @GetMapping("/goal/{goalId}")
    public ResponseEntity<List<SavingsContributionDTO>> getContributionsByGoal(@PathVariable Integer goalId) {
        return ResponseEntity.ok(contributionService.getAllByGoal(goalId));
    }

    //  Get Contributions by User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavingsContributionDTO>> getContributionsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(contributionService.getAllByUser(userId));
    }

    //  Update Contribution
    @PutMapping("/{id}")
    public ResponseEntity<SavingsContributionDTO> updateContribution(
            @PathVariable Integer id,
            @RequestBody SavingsContributionDTO dto) {
        return ResponseEntity.ok(contributionService.updateContribution(id, dto));
    }

    //  Delete Contribution
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContribution(@PathVariable Integer id) {
        contributionService.deleteContribution(id);
        return ResponseEntity.ok("Contribution deleted successfully");
    }
}
