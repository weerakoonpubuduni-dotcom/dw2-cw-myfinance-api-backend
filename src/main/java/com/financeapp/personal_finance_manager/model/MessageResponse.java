package com.financeapp.personal_finance_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;

    public MessageResponse(String invalidCredentials) {
    }
}
