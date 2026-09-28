package com.opendatajungle.commons.infra.service;

import com.opendatajungle.commons.business.service.AuthenticationUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SecurityContextAuthenticationService implements AuthenticationUseCase {

    @Override
    public String getCurrentUser() {
        return findCurrentUserUsername().orElse(DEFAULT_UNKNOWN_USERNAME);
    }

    @Override
    public Optional<String> findCurrentUserUsername() {
        return getCurrentJwt()
                .map(this::extractUsername)
                .filter(username -> !username.isBlank());
    }

    /**
     * Falls back on the immutable "sub" claim so that a token without "preferred_username"
     * never degrades to the shared anonymous identity.
     */
    private String extractUsername(Jwt jwt) {
        String preferredUsername = jwt.getClaimAsString("preferred_username");
        return preferredUsername != null && !preferredUsername.isBlank() ? preferredUsername : jwt.getSubject();
    }

    @Override
    public Optional<String> findCurrentUserAuthId() {
        return getCurrentJwt()
                .map(Jwt::getSubject)
                .filter(value -> !value.isBlank());
    }

    @Override
    public Optional<String> findCurrentUserAuthIss() {
        return getCurrentJwt()
                .map(jwt -> jwt.getClaimAsString(JwtClaimNames.ISS))
                .filter(value -> !value.isBlank());
    }

    @Override
    public Optional<String> findCurrentUserFirstName() {
        return getCurrentJwt()
                .map(jwt -> jwt.getClaimAsString("given_name"))
                .filter(name -> !name.isBlank());
    }

    @Override
    public Optional<String> findCurrentUserLastName() {
        return getCurrentJwt()
                .map(jwt -> jwt.getClaimAsString("family_name"))
                .filter(name -> !name.isBlank());
    }


    @Override
    public List<String> getAuthorities() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(auth -> auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<String> getToken() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(Jwt.class::isInstance)
                .map(Jwt.class::cast)
                .map(Jwt::getTokenValue);
    }

    private Optional<Jwt> getCurrentJwt() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(Jwt.class::isInstance)
                .map(Jwt.class::cast);
    }
}

