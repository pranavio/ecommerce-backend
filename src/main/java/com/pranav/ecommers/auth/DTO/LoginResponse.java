package com.pranav.ecommers.auth.DTO;

import lombok.Data;

@Data
public class LoginResponse {
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String accessToken;
}
