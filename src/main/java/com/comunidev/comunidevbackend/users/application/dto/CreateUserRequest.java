package com.comunidev.comunidevbackend.users.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create a new user")
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    @Schema(description = "User's full name", example = "Carlos Ruiz")
    private String nombre;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Schema(description = "Unique username", example = "carlosdev")
    private String nombreUsuario;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(description = "User's email", example = "carlos@correo.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(description = "User's password", example = "password123")
    private String password;

    @Schema(description = "User role", example = "DEVELOPER", allowableValues = {"DEVELOPER", "RECRUITER"})
    private String rol = "DEVELOPER";
}
