package com.comunidev.comunidevbackend.users.application.service;

import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.users.application.dto.CreateUserRequest;
import com.comunidev.comunidevbackend.users.application.dto.UserResponse;
import com.comunidev.comunidevbackend.users.application.port.in.CreateUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import com.comunidev.comunidevbackend.users.domain.User;
import com.comunidev.comunidevbackend.users.domain.UserRole;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepositoryPort.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        if (userRepositoryPort.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new BusinessException("Username already exists");
        }

        UserRole rol = UserRole.valueOf(request.getRol());
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.create(
                request.getNombre(),
                request.getNombreUsuario(),
                request.getEmail(),
                hashedPassword,
                rol
        );

        User savedUser = userRepositoryPort.save(user);
        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setNombre(user.getNombre());
        response.setNombreUsuario(user.getNombreUsuario());
        response.setEmail(user.getEmail());
        response.setFotoPerfilUrl(user.getFotoPerfilUrl());
        response.setBannerUrl(user.getBannerUrl());
        response.setRoles(user.getRoles().stream()
                .map(UserRole::name)
                .collect(Collectors.toSet()));
        response.setRolActivo(user.getRolActivo().name());
        response.setEstadoCuenta(user.getEstadoCuenta().name());
        response.setEmailVerificado(user.getEmailVerificado());
        response.setSeguidoresCount(user.getSeguidoresCount());
        response.setSiguiendoCount(user.getSiguiendoCount());
        response.setConexionesCount(user.getConexionesCount());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
