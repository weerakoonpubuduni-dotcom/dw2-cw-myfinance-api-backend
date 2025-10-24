package com.financeapp.personal_finance_manager.entity;


@Entity
@Table(name = "sync_log")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sync_id")
    private Long syncId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "table_name", nullable = false, length = 50)
    private String tableName;

    @Column(name = "record_id", nullable = false)
    private Long recordId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Operation operation;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status", length = 20)
    private SyncStatus syncStatus = SyncStatus.PENDING;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "synced_at")
    private LocalDateTime syncedAt;

    public enum Operation {
        INSERT, UPDATE, DELETE
    }

    public enum SyncStatus {
        PENDING, SUCCESS, FAILED
    }
}
