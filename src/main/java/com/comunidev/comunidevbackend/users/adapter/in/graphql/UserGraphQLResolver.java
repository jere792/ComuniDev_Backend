package com.comunidev.comunidevbackend.users.adapter.in.graphql;

import com.comunidev.comunidevbackend.users.application.dto.UpdateUserRequest;
import com.comunidev.comunidevbackend.users.application.dto.UserResponse;
import com.comunidev.comunidevbackend.users.application.port.in.GetUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.in.UpdateUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserGraphQLResolver {

    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UserRepositoryPort userRepositoryPort;

    @QueryMapping
    public List<UserResponse> users() {
        return getUserUseCase.getAllUsers();
    }

    @QueryMapping
    public UserResponse user(@Argument String id) {
        return getUserUseCase.getUserById(id)
                .orElse(null);
    }

    @QueryMapping
    public UserResponse me() {
        // TODO: obtener userId del JWT token
        // Por ahora retorna null
        return null;
    }

    @MutationMapping
    public UserResponse updateUser(
            @Argument String id,
            @Argument String nombre,
            @Argument String nombreUsuario,
            @Argument String email,
            @Argument String fotoPerfilUrl,
            @Argument String bannerUrl) {
        
        UpdateUserRequest request = new UpdateUserRequest();
        request.setNombre(nombre);
        request.setNombreUsuario(nombreUsuario);
        request.setEmail(email);
        request.setFotoPerfilUrl(fotoPerfilUrl);
        request.setBannerUrl(bannerUrl);
        
        return updateUserUseCase.updateUser(id, request);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument String id) {
        try {
            userRepositoryPort.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
