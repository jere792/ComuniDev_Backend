package com.comunidev.comunidevbackend.auth.adapter.in.rest;

import com.comunidev.comunidevbackend.auth.application.dto.LoginRequest;
import com.comunidev.comunidevbackend.auth.application.dto.LoginResponse;
import com.comunidev.comunidevbackend.auth.application.service.GitHubOAuthService;
import com.comunidev.comunidevbackend.auth.application.service.LoginService;
import com.comunidev.comunidevbackend.shared.adapter.in.rest.dto.ApiResponse;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.users.domain.User;
import com.comunidev.comunidevbackend.users.domain.UserRole;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

    private final LoginService loginService;
    private final GitHubOAuthService gitHubOAuthService;
    private final UserRepositoryPort userRepositoryPort;

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginService.login(request);
        return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
    }

    @GetMapping("/github")
    @Operation(summary = "Redirect to GitHub for OAuth login")
    public void githubLogin(HttpServletResponse response) throws IOException {
        String url = gitHubOAuthService.getGitHubAuthorizeUrl();
        response.sendRedirect(url);
    }

    @Value("${frontend.url:http://localhost:4200}")
    private String frontendUrl;

    @GetMapping("/github/callback")
    @Operation(summary = "GitHub OAuth callback - redirects to frontend with user data")
    public void githubCallback(@RequestParam(required = false) String code,
                               @RequestParam(required = false) String error,
                               HttpServletResponse response) throws IOException {
        // Handle user cancellation or GitHub errors
        if (error != null) {
            response.sendRedirect(frontendUrl + "/auth/callback?error=" + encode(error));
            return;
        }

        // Handle missing code
        if (code == null || code.isEmpty()) {
            response.sendRedirect(frontendUrl + "/auth/callback?error=missing_code");
            return;
        }

        try {
            LoginResponse data = gitHubOAuthService.handleCallback(code);
            String redirectUrl = frontendUrl + "/auth/callback"
                    + "?token=" + encode(data.getToken())
                    + "&id=" + encode(data.getId())
                    + "&nombre=" + encode(data.getNombre() != null ? data.getNombre() : "")
                    + "&email=" + encode(data.getEmail() != null ? data.getEmail() : "")
                    + "&needsRole=" + data.getNeedsRoleSelection();
            if (data.getRolActivo() != null) {
                redirectUrl += "&role=" + encode(data.getRolActivo().toLowerCase());
            }
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            response.sendRedirect(frontendUrl + "/auth/callback?error=" + encode(e.getMessage()));
        }
    }

    @PostMapping("/complete-role")
    @Operation(summary = "Complete role selection after GitHub OAuth")
    public ResponseEntity<ApiResponse<LoginResponse>> completeRole(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String rol = body.get("rol");

        if (userId == null || rol == null) {
            throw new BusinessException("userId y rol son requeridos");
        }

        UserRole userRole;
        try {
            userRole = UserRole.valueOf(rol.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Rol invalido: " + rol);
        }

        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        user.setRolActivo(userRole);
        user.setRoles(new HashSet<>(Set.of(userRole)));
        userRepositoryPort.save(user);

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

        return ResponseEntity.ok(ApiResponse.ok("Role assigned successfully", response));
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
