package com.finance.system.service;

import com.finance.system.dto.request.CreateUserRequest;
import com.finance.system.dto.request.UpdateUserRequest;
import com.finance.system.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    
    UserResponse createUser(CreateUserRequest request);
    
    UserResponse updateUser(Long id, UpdateUserRequest request);
    
    UserResponse getUserById(Long id);
    
    List<UserResponse> getAllUsers();
    
    void deleteUser(Long id);
}
