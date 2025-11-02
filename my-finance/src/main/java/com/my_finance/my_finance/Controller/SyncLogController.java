package com.my_finance.my_finance.Controller;


import com.my_finance.my_finance.DTO.SyncLogDTO;
import com.my_finance.my_finance.Service.SyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sync-logs")
//@CrossOrigin(origins = "*")
public class SyncLogController {

    @Autowired private SyncLogService syncLogService;

    //  Create
    @PostMapping
    public ResponseEntity<SyncLogDTO> createSyncLog(@RequestBody SyncLogDTO dto) {
        return ResponseEntity.ok(syncLogService.createSyncLog(dto));
    }

    // get all logs
    @GetMapping
    public ResponseEntity<List<SyncLogDTO>> getAllSyncLogs() {
        return ResponseEntity.ok(syncLogService.getAllSyncLogs());
    }

    // get sync logs by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SyncLogDTO>> getSyncLogsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(syncLogService.getSyncLogsByUser(userId));
    }

    // get logs by id
    @GetMapping("/{id}")
    public ResponseEntity<SyncLogDTO> getSyncLogById(@PathVariable Integer id) {
        return ResponseEntity.ok(syncLogService.getSyncLogById(id));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<SyncLogDTO> updateSyncLog(@PathVariable Integer id, @RequestBody SyncLogDTO dto) {
        return ResponseEntity.ok(syncLogService.updateSyncLog(id, dto));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSyncLog(@PathVariable Integer id) {
        syncLogService.deleteSyncLog(id);
        return ResponseEntity.ok("SyncLog deleted successfully");
    }
}
