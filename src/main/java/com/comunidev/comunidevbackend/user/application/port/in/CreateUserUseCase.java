package com.comunidev.comunidevbackend.user.application.port.in;

import com.comunidev.comunidevbackend.user.application.dto.CreateUserRequest;
import com.comunidev.comunidevbackend.user.application.dto.UserResponse;

public interface CreateUserUseCase {
    UserResponse createUser(CreateUserRequest request);
}
