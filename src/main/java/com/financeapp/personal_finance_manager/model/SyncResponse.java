package com.financeapp.personal_finance_manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncResponse {
    private String status;
    private String message;
    private Integer recordsSynced;
    private Integer recordsFailed;
    private LocalDateTime syncTimestamp;
    private String details;
}
