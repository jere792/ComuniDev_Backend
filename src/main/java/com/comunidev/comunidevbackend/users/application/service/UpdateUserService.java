package com.comunidev.comunidevbackend.users.application.service;

import com.comunidev.comunidevbackend.shared.application.UseCase;
import com.comunidev.comunidevbackend.shared.exception.ResourceNotFoundException;
import com.comunidev.comunidevbackend.users.application.dto.UpdateUserRequest;
import com.comunidev.comunidevbackend.users.application.dto.UserResponse;
import com.comunidev.comunidevbackend.users.application.port.in.UpdateUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import com.comunidev.comunidevbackend.users.domain.User;
import com.comunidev.comunidevbackend.users.domain.UserRole;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserResponse updateUser(String userId, UpdateUserRequest request) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (request.getNombre() != null) {
            user.setNombre(request.getNombre());
        }
        if (request.getNombreUsuario() != null) {
            user.setNombreUsuario(request.getNombreUsuario());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getFotoPerfilUrl() != null) {
            user.setFotoPerfilUrl(request.getFotoPerfilUrl());
        }
        if (request.getBannerUrl() != null) {
            user.setBannerUrl(request.getBannerUrl());
        }
        if (request.getRolActivo() != null) {
            UserRole rol = UserRole.valueOf(request.getRolActivo());
            if (user.hasRole(rol)) {
                user.setRolActivo(rol);
            }
        }

        user.setUpdatedAt(Instant.now());
        User updatedUser = userRepositoryPort.save(user);
        return mapToResponse(updatedUser);
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
