package com.comunidev.comunidevbackend.users.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class User extends AggregateRoot<String> {
    private String nombre;
    private String nombreUsuario;
    private String email;
    private String passwordHash;
    private String fotoPerfilUrl;
    private String bannerUrl;
    private Set<UserRole> roles = new HashSet<>();
    private UserRole rolActivo;
    private UserStatus estadoCuenta;
    private Boolean emailVerificado = false;
    private ActivityStatus estadoActividad;
    private UserConfiguration configuracion;
    private Integer seguidoresCount = 0;
    private Integer siguiendoCount = 0;
    private Integer conexionesCount = 0;
    private BanInfo baneo;
    private Instant createdAt;
    private Instant updatedAt;

    @Getter
    @Setter
    public static class ActivityStatus {
        private String estado = "OFFLINE";
        private String mensajePersonalizado;
        private Instant ultimaVez;
    }

    @Getter
    @Setter
    public static class UserConfiguration {
        private String tema = "SYSTEM";
        private String idioma = "es";
        private NotificationConfig notificaciones = new NotificationConfig();
        private PrivacyConfig privacidad = new PrivacyConfig();
    }

    @Getter
    @Setter
    public static class NotificationConfig {
        private Boolean push = true;
        private Boolean email = true;
        private Boolean mensajes = true;
        private Boolean comentarios = true;
        private Boolean reacciones = true;
        private Boolean conexiones = true;
        private Boolean vacantes = true;
    }

    @Getter
    @Setter
    public static class PrivacyConfig {
        private String perfil = "PUBLIC";
        private String experiencias = "CONNECTIONS";
        private String educacion = "CONNECTIONS";
        private String certificados = "CONNECTIONS";
        private String cv = "CONNECTIONS";
        private String proyectos = "PUBLIC";
        private String tecnologias = "PUBLIC";
        private String permiteMensajesDe = "CONNECTIONS";
        private Boolean mostrarEmail = false;
        private Boolean mostrarUbicacion = false;
        private Boolean mostrarEstadoActividad = true;
    }

    @Getter
    @Setter
    public static class BanInfo {
        private Boolean baneado = false;
        private String tipo;
        private String razon;
        private Instant fechaInicio;
        private Instant fechaFin;
        private String moderadorId;
        private Boolean contenidoOcultoGradualmente = false;
    }

    public static User create(String nombre, String nombreUsuario, String email, String passwordHash, UserRole rol) {
        User user = new User();
        user.setNombre(nombre);
        user.setNombreUsuario(nombreUsuario);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRoles(new HashSet<>());
        user.getRoles().add(rol);
        user.setRolActivo(rol);
        user.setEstadoCuenta(UserStatus.PENDING_VERIFICATION);
        user.setEmailVerificado(false);
        user.setEstadoActividad(new ActivityStatus());
        user.setConfiguracion(new UserConfiguration());
        user.setBaneo(new BanInfo());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return user;
    }

    public Boolean hasRole(UserRole role) {
        return roles != null && roles.contains(role);
    }

    public Boolean isBanned() {
        return baneo != null && Boolean.TRUE.equals(baneo.getBaneado());
    }
}
