package com.comunidev.comunidevbackend.users.application.port.in;

import com.comunidev.comunidevbackend.users.application.dto.UpdateUserRequest;
import com.comunidev.comunidevbackend.users.application.dto.UserResponse;

public interface UpdateUserUseCase {
    UserResponse updateUser(String userId, UpdateUserRequest request);
}
