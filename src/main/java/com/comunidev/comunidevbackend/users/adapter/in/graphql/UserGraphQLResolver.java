package com.comunidev.comunidevbackend.users.adapter.in.graphql;

import com.comunidev.comunidevbackend.users.application.dto.UpdateUserRequest;
import com.comunidev.comunidevbackend.users.application.dto.UserResponse;
import com.comunidev.comunidevbackend.users.application.port.in.GetUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.in.UpdateUserUseCase;
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
        request.setFotoPerfilUrl(fotoPerfilUrl);
        request.setBannerUrl(bannerUrl);
        
        return updateUserUseCase.updateUser(id, request);
    }
}
