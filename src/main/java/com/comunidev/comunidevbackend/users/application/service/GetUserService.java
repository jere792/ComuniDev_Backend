package com.comunidev.comunidevbackend.users.application.service;

import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.users.application.dto.UserResponse;
import com.comunidev.comunidevbackend.users.application.port.in.GetUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import com.comunidev.comunidevbackend.users.domain.User;
import com.comunidev.comunidevbackend.users.domain.UserRole;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Optional<UserResponse> getUserById(String id) {
        return userRepositoryPort.findById(id).map(this::mapToResponse);
    }

    @Override
    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepositoryPort.findByEmail(email).map(this::mapToResponse);
    }

    @Override
    public Optional<UserResponse> getUserByUsername(String username) {
        return userRepositoryPort.findByNombreUsuario(username).map(this::mapToResponse);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepositoryPort.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setNombre(user.getNombre());
        response.setNombreUsuario(user.getNombreUsuario());
        response.setEmail(user.getEmail());
        response.setFotoPerfilUrl(user.getFotoPerfilUrl());
        response.setBannerUrl(user.getBannerUrl());
        if (user.getRoles() != null) {
            response.setRoles(user.getRoles().stream()
                    .map(UserRole::name)
                    .collect(Collectors.toSet()));
        }
        response.setRolActivo(user.getRolActivo() != null ? user.getRolActivo().name() : null);
        response.setEstadoCuenta(user.getEstadoCuenta() != null ? user.getEstadoCuenta().name() : null);
        response.setEmailVerificado(user.getEmailVerificado());
        response.setSeguidoresCount(user.getSeguidoresCount());
        response.setSiguiendoCount(user.getSiguiendoCount());
        response.setConexionesCount(user.getConexionesCount());
        response.setCreatedAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
        return response;
    }
}
