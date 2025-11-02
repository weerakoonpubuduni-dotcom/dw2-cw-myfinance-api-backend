package com.my_finance.my_finance.Service;

import com.my_finance.my_finance.DTO.SyncLogDTO;
import com.my_finance.my_finance.Entity.SyncLog;
import com.my_finance.my_finance.Entity.User;
import com.my_finance.my_finance.Repository.SyncLogRepository;
import com.my_finance.my_finance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SyncLogService {

    @Autowired private SyncLogRepository syncLogRepository;
    @Autowired private UserRepository userRepository;

    // Create a SyncLog entry
    public SyncLogDTO createSyncLog(SyncLogDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SyncLog syncLog = new SyncLog();
        syncLog.setUser(user);
        syncLog.setTableName(dto.getTableName());
        syncLog.setRecordId(dto.getRecordId());
        syncLog.setOperation(dto.getOperation());
        syncLog.setSyncStatus(dto.getSyncStatus());
        syncLog.setErrorMessage(dto.getErrorMessage());
        syncLog.setCreatedAt(LocalDateTime.now());
        syncLog.setSyncedAt(dto.getSyncedAt());

        return convertToDTO(syncLogRepository.save(syncLog));
    }

    //  Get all sync logs
    public List<SyncLogDTO> getAllSyncLogs() {
        return syncLogRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get sync logs by user
    public List<SyncLogDTO> getSyncLogsByUser(Integer userId) {
        return syncLogRepository.findByUserUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get sync log by ID
    public SyncLogDTO getSyncLogById(Integer id) {
        SyncLog log = syncLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SyncLog not found"));
        return convertToDTO(log);
    }

    //Update sync log
    public SyncLogDTO updateSyncLog(Integer id, SyncLogDTO dto) {
        SyncLog log = syncLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SyncLog not found"));

        log.setTableName(dto.getTableName());
        log.setRecordId(dto.getRecordId());
        log.setOperation(dto.getOperation());
        log.setSyncStatus(dto.getSyncStatus());
        log.setErrorMessage(dto.getErrorMessage());
        log.setSyncedAt(LocalDateTime.now());

        return convertToDTO(syncLogRepository.save(log));
    }

    // Delete sync log
    public void deleteSyncLog(Integer id) {
        if (!syncLogRepository.existsById(id)) {
            throw new RuntimeException("SyncLog not found");
        }
        syncLogRepository.deleteById(id);
    }

    //  Entity → DTO
    private SyncLogDTO convertToDTO(SyncLog log) {
        SyncLogDTO dto = new SyncLogDTO();
        dto.setSyncId(log.getSyncId());
        dto.setUserId(log.getUser().getUserId());
        dto.setTableName(log.getTableName());
        dto.setRecordId(log.getRecordId());
        dto.setOperation(log.getOperation());
        dto.setSyncStatus(log.getSyncStatus());
        dto.setErrorMessage(log.getErrorMessage());
        dto.setCreatedAt(log.getCreatedAt());
        dto.setSyncedAt(log.getSyncedAt());
        return dto;
    }

}
