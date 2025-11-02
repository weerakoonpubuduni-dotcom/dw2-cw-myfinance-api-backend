package com.my_finance.my_finance.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sync_log")
public class SyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer syncId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="table_name")
    private String tableName;

    @Column(name="record_id")
    private Integer recordId;

    @Column(name="operation")
    private String operation;

    @Column(name="sync_status")
    private String syncStatus;

    @Column(name="error_message")
    private String errorMessage;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="synced_at")
    private LocalDateTime syncedAt;

    // Getters and Setters
    public Integer getSyncId() { return syncId; }
    public void setSyncId(Integer syncId) { this.syncId = syncId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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
