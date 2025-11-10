package com.my_finance.my_finance.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AutoSyncService {

    @Autowired
    @Qualifier("sqliteDataSource")
    private javax.sql.DataSource sqliteDataSource;

    @Autowired
    @Qualifier("oracleDataSource")
    private javax.sql.DataSource oracleDataSource;

    private final int SYNC_INTERVAL_MS = 5000; // every 5 seconds

    @Scheduled(fixedDelay = SYNC_INTERVAL_MS)
    public void syncUnsyncedRecords() {
        JdbcTemplate sqlite = new JdbcTemplate(sqliteDataSource);
        JdbcTemplate oracle = new JdbcTemplate(oracleDataSource);

        try {
            syncTable(sqlite, oracle, "users", "user_id");
            syncTable(sqlite, oracle, "categories", "category_id");
            syncTable(sqlite, oracle, "expenses", "expense_id");
            syncTable(sqlite, oracle, "income", "income_id");
            syncTable(sqlite, oracle, "budgets", "budget_id");
            syncTable(sqlite, oracle, "savings_goals", "goal_id");
            syncTable(sqlite, oracle, "savings_contributions", "contribution_id");

            System.out.println("SQLite → Oracle sync completed at " + java.time.LocalTime.now());
        } catch (Exception e) {
            System.err.println(" Sync failed: " + e.getMessage());
        }
    }

    private void syncTable(JdbcTemplate sqlite, JdbcTemplate oracle, String table, String idCol) {
        List<Map<String, Object>> unsynced = sqlite.queryForList("SELECT * FROM " + table + " WHERE is_synced = 0");

        int successCount = 0;
        int failCount = 0;

        for (Map<String, Object> record : unsynced) {
            try {
                // Delete existing record in Oracle
                oracle.update("DELETE FROM " + table + " WHERE " + idCol + " = ?", record.get(idCol));

                // Prepare Insert
                String columns = String.join(", ", record.keySet());
                String placeholders = String.join(", ", record.keySet().stream().map(k -> "?").toList());
                oracle.update("INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")", record.values().toArray());

                // Mark synced in SQLite
                sqlite.update("UPDATE " + table + " SET is_synced = 1, sync_timestamp = CURRENT_TIMESTAMP WHERE " + idCol + "=?", record.get(idCol));

                successCount++;

                // Also mark Oracle row
                oracle.update("UPDATE " + table + " SET synced_from_local = 1 WHERE " + idCol + "=?", record.get(idCol));

            } catch (Exception e) {
                failCount++;
                sqlite.update(
                        "INSERT INTO sync_log (user_id, table_name, record_id, operation, sync_status, error_message, created_at) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                        record.get("user_id"), table, record.get(idCol), "INSERT/UPDATE", "FAILED", e.getMessage()
                );
            }
        }

        // Log to Oracle sync_audit
        oracle.update("""
                INSERT INTO sync_audit (user_id, table_name, operation, record_count, sync_status, sync_started_at, sync_completed_at)
                VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """,
                unsynced.isEmpty() ? 0 : unsynced.get(0).get("user_id"),
                table,
                "INSERT/UPDATE",
                successCount,
                failCount == 0 ? "SUCCESS" : (successCount > 0 ? "PARTIAL" : "FAILED")
        );
    }

}
