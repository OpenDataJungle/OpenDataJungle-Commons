package com.opendatajungle.commons.infra.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LocalAuthenticationServiceTest {

    private final LocalAuthenticationService service = new LocalAuthenticationService();

    @Test
    void getCurrentUser_shouldReturnDefaultUsername() {
        assertThat(service.getCurrentUser()).isEqualTo(LocalAuthenticationService.DEFAULT_USERNAME);
    }

    @Test
    void findCurrentUserUsername_shouldReturnDefaultUsername() {
        assertThat(service.findCurrentUserUsername()).contains(LocalAuthenticationService.DEFAULT_USERNAME);
    }

    @Test
    void findCurrentUserAuthId_shouldReturnDefaultAuthId() {
        assertThat(service.findCurrentUserAuthId()).contains(LocalAuthenticationService.DEFAULT_AUTH_ID);
    }

    @Test
    void findCurrentUserAuthIss_shouldReturnDefaultAuthIss() {
        assertThat(service.findCurrentUserAuthIss()).contains(LocalAuthenticationService.DEFAULT_AUTH_ISS);
    }

    @Test
    void findCurrentUserFirstName_shouldReturnDefaultFirstName() {
        assertThat(service.findCurrentUserFirstName()).contains(LocalAuthenticationService.DEFAULT_FIRST_NAME);
    }

    @Test
    void findCurrentUserLastName_shouldReturnDefaultLastName() {
        assertThat(service.findCurrentUserLastName()).contains(LocalAuthenticationService.DEFAULT_LAST_NAME);
    }

    @Test
    void getAuthorities_shouldReturnEmptyList() {
        assertThat(service.getAuthorities()).isEqualTo(List.of());
    }

    @Test
    void getToken_shouldReturnEmpty() {
        assertThat(service.getToken()).isEqualTo(Optional.empty());
    }
}
