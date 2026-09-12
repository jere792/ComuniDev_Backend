package com.comunidev.comunidevbackend.users.adapter.in.graphql;

import com.comunidev.comunidevbackend.developer_profile.application.port.out.DeveloperProfileRepositoryPort;
import com.comunidev.comunidevbackend.recruiter_profile.application.port.out.RecruiterProfileRepositoryPort;
import com.comunidev.comunidevbackend.users.application.dto.UpdateUserRequest;
import com.comunidev.comunidevbackend.users.application.dto.UserResponse;
import com.comunidev.comunidevbackend.users.application.port.in.GetUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.in.UpdateUserUseCase;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import com.comunidev.comunidevbackend.users.domain.User;
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
    private final DeveloperProfileRepositoryPort developerProfileRepositoryPort;
    private final RecruiterProfileRepositoryPort recruiterProfileRepositoryPort;

    @QueryMapping
    public List<UserResponse> users() {
        return getUserUseCase.getAllUsers();
    }

    @QueryMapping
    public UserResponse user(@Argument String id) {
        return getUserUseCase.getUserById(id)
                .map(response -> {
                    if ("DEVELOPER".equals(response.getRolActivo())) {
                        developerProfileRepositoryPort.findByUserId(id)
                                .ifPresent(response::setDeveloperProfile);
                    } else if ("RECRUITER".equals(response.getRolActivo())) {
                        recruiterProfileRepositoryPort.findByUserId(id)
                                .ifPresent(response::setRecruiterProfile);
                    }
                    return response;
                })
                .orElse(null);
    }

    @QueryMapping
    public UserResponse me() {
        // TODO: obtener userId del JWT token
        return null;
    }

    @MutationMapping
    public UserResponse updateUser(
            @Argument String id,
            @Argument String nombre,
            @Argument String nombreUsuario,
            @Argument String email,
            @Argument String telefono,
            @Argument String fotoPerfilUrl,
            @Argument String bannerUrl,
            @Argument String bio,
            @Argument User.Ubicacion ubicacion) {
        
        UpdateUserRequest request = new UpdateUserRequest();
        request.setNombre(nombre);
        request.setNombreUsuario(nombreUsuario);
        request.setEmail(email);
        request.setTelefono(telefono);
        request.setFotoPerfilUrl(fotoPerfilUrl);
        request.setBannerUrl(bannerUrl);
        request.setBio(bio);
        request.setUbicacion(ubicacion);
        
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
