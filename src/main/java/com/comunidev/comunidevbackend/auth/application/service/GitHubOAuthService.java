package com.comunidev.comunidevbackend.auth.application.service;

import com.comunidev.comunidevbackend.auth.application.dto.LoginResponse;
import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.users.domain.User;
import com.comunidev.comunidevbackend.users.domain.UserRole;
import com.comunidev.comunidevbackend.users.domain.UserStatus;
import com.comunidev.comunidevbackend.users.application.port.out.UserRepositoryPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubOAuthService {

    private final UserRepositoryPort userRepositoryPort;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    private static final String GITHUB_AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USER_URL = "https://api.github.com/user";

    public String getGitHubAuthorizeUrl() {
        return GITHUB_AUTHORIZE_URL
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=user:email";
    }

    public LoginResponse handleCallback(String code) {
        String accessToken = exchangeCodeForToken(code);
        JsonNode githubUser = fetchGitHubUser(accessToken);
        String githubEmail = fetchGitHubEmail(accessToken);

        String githubId = githubUser.get("id").asText();
        String nombre = githubUser.has("name") && !githubUser.get("name").isNull()
                ? githubUser.get("name").asText()
                : githubUser.get("login").asText();
        String avatarUrl = githubUser.has("avatar_url") ? githubUser.get("avatar_url").asText() : null;
        String login = githubUser.get("login").asText();

        Optional<User> existingUser = userRepositoryPort.findByEmail(githubEmail);

        boolean isNewUser = existingUser.isEmpty();
        User user;

        if (isNewUser) {
            user = new User();
            user.setNombre(nombre);
            user.setNombreUsuario(login);
            user.setEmail(githubEmail);
            user.setPasswordHash("");
            user.setFotoPerfilUrl(avatarUrl);
            user.setBannerUrl("");
            user.setRoles(new HashSet<>());
            user.setRolActivo(null);
            user.setEstadoCuenta(UserStatus.ACTIVE);
            user.setEmailVerificado(true);
            user.setEstadoActividad(new User.ActivityStatus());
            user.setConfiguracion(new User.UserConfiguration());
            user.setBaneo(new User.BanInfo());
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(Instant.now());
        } else {
            user = existingUser.get();
            if (avatarUrl != null) {
                user.setFotoPerfilUrl(avatarUrl);
            }
        }

        User savedUser = userRepositoryPort.save(user);

        LoginResponse response = new LoginResponse();
        response.setId(savedUser.getId());
        response.setNombre(savedUser.getNombre());
        response.setNombreUsuario(savedUser.getNombreUsuario());
        response.setEmail(savedUser.getEmail());
        response.setRoles(savedUser.getRoles().stream()
                .map(UserRole::name)
                .collect(Collectors.toSet()));
        response.setRolActivo(savedUser.getRolActivo() != null ? savedUser.getRolActivo().name() : null);
        response.setEstadoCuenta(savedUser.getEstadoCuenta().name());
        response.setToken("token-temporal-" + savedUser.getId());
        response.setNeedsRoleSelection(isNewUser);

        return response;
    }

    private String exchangeCodeForToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(GITHUB_TOKEN_URL, request, String.class);
            JsonNode json = objectMapper.readTree(response.getBody());

            if (json.has("error")) {
                log.error("GitHub token exchange error: {}", json.get("error_description").asText());
                throw new BusinessException("Error al autenticar con GitHub: " + json.get("error_description").asText());
            }

            return json.get("access_token").asText();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error exchanging code for token", e);
            throw new BusinessException("Error al autenticar con GitHub");
        }
    }

    private JsonNode fetchGitHubUser(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    GITHUB_USER_URL, HttpMethod.GET, request, String.class);
            return objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            log.error("Error fetching GitHub user", e);
            throw new BusinessException("Error al obtener datos del usuario de GitHub");
        }
    }

    private String fetchGitHubEmail(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    GITHUB_USER_URL + "/emails", HttpMethod.GET, request, String.class);
            JsonNode emails = objectMapper.readTree(response.getBody());

            for (JsonNode emailNode : emails) {
                if (emailNode.get("primary").asBoolean()) {
                    return emailNode.get("email").asText();
                }
            }

            if (emails.isArray() && emails.size() > 0) {
                return emails.get(0).get("email").asText();
            }

            throw new BusinessException("No se encontro email en GitHub");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching GitHub email", e);
            throw new BusinessException("Error al obtener email de GitHub");
        }
    }
}
