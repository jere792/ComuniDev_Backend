package com.comunidev.comunidevbackend.users.application.port.in;

import com.comunidev.comunidevbackend.users.application.dto.CreateUserRequest;
import com.comunidev.comunidevbackend.users.application.dto.UserResponse;

public interface CreateUserUseCase {
    UserResponse createUser(CreateUserRequest request);
}
