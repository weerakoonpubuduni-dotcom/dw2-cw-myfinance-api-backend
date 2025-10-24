package com.financeapp.personal_finance_manager.model;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private String fullName;

    public JwtResponse(String token, Long userId, String username, String email, String fullName) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }
}
