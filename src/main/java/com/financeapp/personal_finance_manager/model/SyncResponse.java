package com.financeapp.personal_finance_manager.model;

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
