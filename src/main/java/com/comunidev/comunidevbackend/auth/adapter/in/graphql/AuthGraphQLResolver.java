package com.comunidev.comunidevbackend.auth.adapter.in.graphql;

import com.comunidev.comunidevbackend.auth.application.dto.LoginRequest;
import com.comunidev.comunidevbackend.auth.application.dto.LoginResponse;
import com.comunidev.comunidevbackend.auth.application.service.ChangePasswordService;
import com.comunidev.comunidevbackend.auth.application.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthGraphQLResolver {

    private final LoginService loginService;
    private final ChangePasswordService changePasswordService;

    @MutationMapping
    public LoginResponse login(@Argument String email, @Argument String password) {
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        return loginService.login(request);
    }

    @MutationMapping
    public Boolean changePassword(
            @Argument String userId,
            @Argument String currentPassword,
            @Argument String newPassword) {
        return changePasswordService.changePassword(userId, currentPassword, newPassword);
    }
}
