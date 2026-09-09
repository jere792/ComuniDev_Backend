package com.comunidev.comunidevbackend.auth.adapter.in.graphql;

import com.comunidev.comunidevbackend.auth.application.dto.LoginRequest;
import com.comunidev.comunidevbackend.auth.application.dto.LoginResponse;
import com.comunidev.comunidevbackend.auth.application.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthGraphQLResolver {

    private final LoginService loginService;

    @MutationMapping
    public LoginResponse login(@Argument String email, @Argument String password) {
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        return loginService.login(request);
    }
}
