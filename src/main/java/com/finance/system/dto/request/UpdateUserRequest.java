package com.finance.system.dto.request;

import com.finance.system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    
    private Role role;
    
    private Boolean active;
}
