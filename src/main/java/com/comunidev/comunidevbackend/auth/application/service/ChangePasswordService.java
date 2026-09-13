package com.comunidev.comunidevbackend.auth.application.service;

import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import com.comunidev.comunidevbackend.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public Boolean changePassword(String userId, String currentPassword, String newPassword) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            throw new BusinessException("Contraseña actual incorrecta");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new BusinessException("La nueva contraseña debe tener al menos 6 caracteres");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(java.time.Instant.now());
        userRepositoryPort.save(user);

        return true;
    }
}
