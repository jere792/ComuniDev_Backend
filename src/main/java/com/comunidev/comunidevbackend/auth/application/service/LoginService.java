package com.comunidev.comunidevbackend.auth.application.service;

import com.comunidev.comunidevbackend.auth.application.dto.LoginRequest;
import com.comunidev.comunidevbackend.auth.application.dto.LoginResponse;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.users.domain.User;
import com.comunidev.comunidevbackend.users.domain.UserRole;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepositoryPort.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Credenciales invalidas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BusinessException("Credenciales invalidas");
        }

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setNombre(user.getNombre());
        response.setNombreUsuario(user.getNombreUsuario());
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles().stream()
                .map(UserRole::name)
                .collect(Collectors.toSet()));
        response.setRolActivo(user.getRolActivo().name());
        response.setEstadoCuenta(user.getEstadoCuenta().name());
        response.setToken("token-temporal-" + user.getId());

        return response;
    }
}
