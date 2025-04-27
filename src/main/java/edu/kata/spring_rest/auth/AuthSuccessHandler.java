package edu.kata.spring_rest.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Получаем роли пользователя
        boolean isAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority
                                .getAuthority()
                                .equals("ROLE_ADMIN")
                );

        boolean isUser = authentication
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority
                                .getAuthority()
                                .equals("ROLE_USER")
                );

        // Перенаправляем в зависимости от роли
        if (isAdmin) {
            response.sendRedirect("/admin/");
        } else if (isUser) {
            response.sendRedirect("/user/");
        } else {
            response.sendRedirect("/");
        }
    }
}
