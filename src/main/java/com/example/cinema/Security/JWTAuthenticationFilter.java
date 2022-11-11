package com.example.cinema.Security;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.example.cinema.Security.SecurityConstants.*;
import static java.util.Collections.emptyList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTokenService jwTokenService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JWTokenService jwTokenService){
        this.authenticationManager = authenticationManager;
        this.jwTokenService = jwTokenService;
    }


    /** Попытка аутентификации пользователя */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {

            // Получение пользователя из запроса и конвертация данных в сущность UserEntity
            UserEntity currentUser = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            currentUser.getEmail(),
                            currentUser.getPassword(),
                            emptyList()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Успешная авторизация
     * Создание и добавление двух токенов в хедеры
     * @code 200 - role
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        Collection<GrantedAuthority> roles = ((User) authResult.getPrincipal()).getAuthorities();
        String role = String.valueOf(roles.iterator().next());
        String email = ((User) authResult.getPrincipal()).getUsername();

        String token = jwTokenService.createJWT(email, role);
        response.addHeader(HEADER_JWT_STRING, TOKEN_PREFIX+token);

//        RefreshToken refreshToken = refreshTokenService.createRTbyUserTelephoneNumber(login);
//        response.addHeader(HEADER_RT_STRING, refreshToken.getToken());

        //Устанавливаем, какие хедеры может видеть фронт
        response.addHeader("Access-Control-Expose-Headers", HEADER_JWT_STRING + "," + HEADER_RT_STRING);

        StaticMethods.createResponse(HttpServletResponse.SC_OK, role);
    }


    /**
     * Безуспешная авторизация
     * @code 401 - Unauthorized
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {
        StaticMethods.createResponse(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

    }
}