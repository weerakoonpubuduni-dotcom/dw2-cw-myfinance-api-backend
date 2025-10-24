package com.financeapp.personal_finance_manager.entity;

@Repository
public interface SyncLogRepository extends JpaRepository<SyncLog, Long> {

    List<SyncLog> findByUserId(Long userId);

    List<SyncLog> findByUserIdAndSyncStatus(
            Long userId, SyncLog.SyncStatus syncStatus
    );

    List<SyncLog> findByUserIdAndTableName(Long userId, String tableName);

    List<SyncLog> findByUserIdOrderByCreatedAtDesc(Long userId);
}
