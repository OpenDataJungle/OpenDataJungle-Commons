package com.opendatajungle.commons.infra.service;

import com.opendatajungle.commons.business.service.AuthenticationUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Stands in for {@link SecurityContextAuthenticationService} on the "local" profile, where
 * {@code WithoutSecurityConfiguration} disables JWT authentication entirely and the security
 * context never carries a user.
 */
public class LocalAuthenticationService implements AuthenticationUseCase {

    public static final String DEFAULT_USERNAME = "anonymous";
    public static final String DEFAULT_AUTH_ID = "00000000-0000-0000-0000-000000000000";
    public static final String DEFAULT_AUTH_ISS = "https://system.internal.localhost";
    public static final String DEFAULT_FIRST_NAME = "Anonymous";
    public static final String DEFAULT_LAST_NAME = "Anonymous";

    @Override
    public String getCurrentUser() {
        return DEFAULT_USERNAME;
    }

    @Override
    public Optional<String> findCurrentUserUsername() {
        return Optional.of(DEFAULT_USERNAME);
    }

    @Override
    public Optional<String> findCurrentUserAuthId() {
        return Optional.of(DEFAULT_AUTH_ID);
    }

    @Override
    public Optional<String> findCurrentUserAuthIss() {
        return Optional.of(DEFAULT_AUTH_ISS);
    }

    @Override
    public Optional<String> findCurrentUserFirstName() {
        return Optional.of(DEFAULT_FIRST_NAME);
    }

    @Override
    public Optional<String> findCurrentUserLastName() {
        return Optional.of(DEFAULT_LAST_NAME);
    }

    @Override
    public List<String> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Optional<String> getToken() {
        return Optional.empty();
    }
}
