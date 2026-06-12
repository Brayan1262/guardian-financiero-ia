package com.brayan.guardianfinanciero.dto.auth;

import com.brayan.guardianfinanciero.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private UserRole role;
    private Boolean enabled;
    private LocalDateTime createdAt;
}
