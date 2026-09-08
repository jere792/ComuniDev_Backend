package com.comunidev.comunidevbackend.users.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update user")
public class UpdateUserRequest {

    @Schema(description = "User's full name", example = "Carlos Ruiz")
    private String nombre;

    @Schema(description = "Profile picture URL")
    private String fotoPerfilUrl;

    @Schema(description = "Banner URL")
    private String bannerUrl;

    @Schema(description = "Active role", example = "DEVELOPER")
    private String rolActivo;
}
