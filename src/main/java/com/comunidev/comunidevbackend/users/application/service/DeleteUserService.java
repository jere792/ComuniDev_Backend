package com.comunidev.comunidevbackend.users.application.service;

import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import com.comunidev.comunidevbackend.users.application.port.in.DeleteUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import com.comunidev.comunidevbackend.users.domain.UserStatus;
import com.comunidev.comunidevbackend.users.domain.User;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@UseCase
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void deleteUser(String userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setEstadoCuenta(UserStatus.DELETED);
        user.setUpdatedAt(Instant.now());
        userRepositoryPort.save(user);
    }
}
