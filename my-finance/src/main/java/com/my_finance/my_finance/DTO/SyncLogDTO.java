package com.my_finance.my_finance.DTO;

import java.time.LocalDateTime;

public class SyncLogDTO {

    private Integer syncId;
    private Integer userId;
    private String tableName;
    private Integer recordId;
    private String operation;
    private String syncStatus;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime syncedAt;


    // Getters and Setters
    public Integer getSyncId() { return syncId; }
    public void setSyncId(Integer syncId) { this.syncId = syncId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public Integer getRecordId() { return recordId; }
    public void setRecordId(Integer recordId) { this.recordId = recordId; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getSyncStatus() { return syncStatus; }
    public void setSyncStatus(String syncStatus) { this.syncStatus = syncStatus; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getSyncedAt() { return syncedAt; }
    public void setSyncedAt(LocalDateTime syncedAt) { this.syncedAt = syncedAt; }
}
