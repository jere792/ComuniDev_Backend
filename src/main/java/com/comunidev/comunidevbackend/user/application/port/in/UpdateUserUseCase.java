package com.comunidev.comunidevbackend.user.application.port.in;

import com.comunidev.comunidevbackend.user.application.dto.UpdateUserRequest;
import com.comunidev.comunidevbackend.user.application.dto.UserResponse;

public interface UpdateUserUseCase {
    UserResponse updateUser(String userId, UpdateUserRequest request);
}
