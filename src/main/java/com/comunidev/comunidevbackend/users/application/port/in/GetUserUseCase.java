package com.comunidev.comunidevbackend.users.application.port.in;

import com.comunidev.comunidevbackend.users.application.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface GetUserUseCase {
    Optional<UserResponse> getUserById(String id);
    Optional<UserResponse> getUserByEmail(String email);
    Optional<UserResponse> getUserByUsername(String username);
    List<UserResponse> getAllUsers();
}
