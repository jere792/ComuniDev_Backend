package com.comunidev.comunidevbackend.user.application.service;

import com.comunidev.comunidevbackend.shared.exception.BusinessException;
import com.comunidev.comunidevbackend.user.application.dto.CreateUserRequest;
import com.comunidev.comunidevbackend.user.application.dto.UserResponse;
import com.comunidev.comunidevbackend.user.application.port.out.UserRepositoryPort;
import com.comunidev.comunidevbackend.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateUserServiceTest {

    private final FakeUserRepository repository = new FakeUserRepository();
    private final CreateUserService service =
            new CreateUserService(repository, new FakePasswordEncoder());

    @Test
    @DisplayName("createUser guarda y devuelve el telefono (regresion: se descartaba en silencio)")
    void createUserGuardaElTelefono() {
        UserResponse created = service.createUser(solicitud("+51999888777"));

        assertEquals("+51999888777", created.getTelefono(), "lo que escribe el usuario debe persistirse");
        assertEquals("+51999888777", repository.findById(created.getId()).orElseThrow().getTelefono());
    }

    @Test
    @DisplayName("createUser acepta telefono vacio")
    void createUserSinTelefonoEsValido() {
        UserResponse created = service.createUser(solicitud(null));

        assertEquals(null, created.getTelefono());
    }

    @Test
    @DisplayName("createUser rechaza un email ya registrado")
    void createUserEmailDuplicadoRechaza() {
        service.createUser(solicitud(null));

        assertThrows(BusinessException.class, () -> service.createUser(solicitud(null)));
    }

    @Test
    @DisplayName("createUser rechaza un nombre de usuario ya registrado")
    void createUserUsernameDuplicadoRechaza() {
        service.createUser(solicitud(null));

        CreateUserRequest repetido = solicitud(null);
        repetido.setEmail("otro@correo.com");

        assertThrows(BusinessException.class, () -> service.createUser(repetido));
    }

    // ---------------------------------------------------------------- helpers

    private CreateUserRequest solicitud(String telefono) {
        CreateUserRequest request = new CreateUserRequest();
        request.setNombre("Carlos Ruiz");
        request.setNombreUsuario("carlosdev");
        request.setEmail("carlos@correo.com");
        request.setPassword("password123");
        request.setRol("DEVELOPER");
        request.setTelefono(telefono);
        return request;
    }

    // ------------------------------------------------------------------ fakes

    private static class FakePasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            return "hashed:" + rawPassword;
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encode(rawPassword).equals(encodedPassword);
        }
    }

    private static class FakeUserRepository implements UserRepositoryPort {
        private final Map<String, User> store = new LinkedHashMap<>();
        private final List<User> order = new ArrayList<>();
        private int seq;

        @Override
        public User save(User user) {
            if (user.getId() == null) {
                user.setId("u-" + (++seq));
                order.add(user);
            }
            store.put(user.getId(), user);
            return user;
        }

        @Override
        public Optional<User> findById(String id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public Optional<User> findByEmail(String email) {
            return order.stream().filter(u -> email.equals(u.getEmail())).findFirst();
        }

        @Override
        public Optional<User> findByNombreUsuario(String nombreUsuario) {
            return order.stream().filter(u -> nombreUsuario.equals(u.getNombreUsuario())).findFirst();
        }

        @Override
        public List<User> findAll() {
            return List.copyOf(order);
        }

        @Override
        public boolean existsByEmail(String email) {
            return findByEmail(email).isPresent();
        }

        @Override
        public boolean existsByNombreUsuario(String nombreUsuario) {
            return findByNombreUsuario(nombreUsuario).isPresent();
        }

        @Override
        public void deleteById(String id) {
            store.remove(id);
        }
    }
}
