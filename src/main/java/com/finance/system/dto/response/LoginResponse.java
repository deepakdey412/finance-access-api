package com.finance.system.dto.response;

import com.finance.system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    
    private String type = "Bearer";
    
    private Long id;
    
    private String username;
    
    private Role role;
    
    public LoginResponse(String token, Long id, String username, Role role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
