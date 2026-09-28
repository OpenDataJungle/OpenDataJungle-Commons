package com.opendatajungle.commons.infra.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityContextAuthenticationServiceTest {

    private final SecurityContextAuthenticationService service = new SecurityContextAuthenticationService();

    @Mock
    private Jwt jwt;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentUser_shouldReturnDefaultUnknown_whenNoAuthentication() {
        // When
        String currentUser = service.getCurrentUser();

        // Then
        assertThat(currentUser).isEqualTo("anonymous");
    }

    @Test
    void getCurrentUser_shouldReturnPreferredUsername_whenPresent() {
        // Given
        when(jwt.getClaimAsString("preferred_username")).thenReturn("alice");
        authenticateWith(jwt);

        // When
        String currentUser = service.getCurrentUser();

        // Then
        assertThat(currentUser).isEqualTo("alice");
    }

    @Test
    void findCurrentUserUsername_shouldFallBackToSubjectClaim_whenPreferredUsernameIsBlank() {
        // Given
        when(jwt.getClaimAsString("preferred_username")).thenReturn(" ");
        when(jwt.getSubject()).thenReturn("jwt-subject");
        authenticateWith(jwt);

        // When
        Optional<String> currentUser = service.findCurrentUserUsername();

        // Then
        assertThat(currentUser).contains("jwt-subject");
    }

    @Test
    void findCurrentUserUsername_shouldBeEmpty_whenUserIsNotAuthenticated() {
        // Given
        Authentication unauthenticated = new TestingAuthenticationToken("alice", "creds");
        unauthenticated.setAuthenticated(false);
        SecurityContextHolder.getContext().setAuthentication(unauthenticated);

        // When
        Optional<String> currentUser = service.findCurrentUserUsername();

        // Then
        assertThat(currentUser).isEmpty();
    }

    @Test
    void findCurrentUserUsername_shouldBeEmpty_whenPrincipalIsNotAJwt() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("alice", "creds"));

        // When
        Optional<String> currentUser = service.findCurrentUserUsername();

        // Then
        assertThat(currentUser).isEmpty();
    }

    @Test
    void findCurrentUserAuthId_shouldReturnSubjectClaim_whenPresent() {
        // Given
        when(jwt.getSubject()).thenReturn("jwt-subject");
        authenticateWith(jwt);

        // When
        Optional<String> authId = service.findCurrentUserAuthId();

        // Then
        assertThat(authId).contains("jwt-subject");
    }

    @Test
    void findCurrentUserAuthId_shouldBeEmpty_whenSubjectIsBlank() {
        // Given
        when(jwt.getSubject()).thenReturn(" ");
        authenticateWith(jwt);

        // When
        Optional<String> authId = service.findCurrentUserAuthId();

        // Then
        assertThat(authId).isEmpty();
    }

    @Test
    void findCurrentUserAuthIss_shouldReturnIssuerClaimAsString_whenNotAValidUrl() {
        // Given
        when(jwt.getClaimAsString("iss")).thenReturn("not a url");
        authenticateWith(jwt);

        // When
        Optional<String> authIss = service.findCurrentUserAuthIss();

        // Then
        assertThat(authIss).contains("not a url");
    }

    @Test
    void getAuthorities_shouldReturnGrantedAuthorities_whenAuthenticated() {
        // Given
        Authentication authentication = new TestingAuthenticationToken(
                "alice", "creds", List.of(new SimpleGrantedAuthority("resources:read")));
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        List<String> authorities = service.getAuthorities();

        // Then
        assertThat(authorities).containsExactly("resources:read");
    }

    @Test
    void getAuthorities_shouldReturnEmptyList_whenNoAuthentication() {
        // When
        List<String> authorities = service.getAuthorities();

        // Then
        assertThat(authorities).isEmpty();
    }

    @Test
    void getToken_shouldReturnJwtTokenValue_whenPrincipalIsAJwt() {
        // Given
        when(jwt.getTokenValue()).thenReturn("token-value");
        authenticateWith(jwt);

        // When
        Optional<String> token = service.getToken();

        // Then
        assertThat(token).contains("token-value");
    }

    @Test
    void getToken_shouldBeEmpty_whenPrincipalIsNotAJwt() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("alice", "creds"));

        // When
        Optional<String> token = service.getToken();

        // Then
        assertThat(token).isEmpty();
    }

    @Test
    void findCurrentUserFirstName_shouldReturnFirstName_whenPrincipalIsAJwt() {
        // Given
        when(jwt.getClaimAsString("given_name")).thenReturn("Alice");
        authenticateWith(jwt);

        // When
        Optional<String> firstName = service.findCurrentUserFirstName();

        // Then
        assertThat(firstName).contains("Alice");
    }

    @Test
    void findCurrentUserFirstName_shouldBeEmpty_whenNoAuthentication() {
        // When
        Optional<String> firstName = service.findCurrentUserFirstName();

        // Then
        assertThat(firstName).isEmpty();
    }

    @Test
    void findCurrentUserFirstName_shouldBeEmpty_whenPrincipalIsNotAJwt() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("alice", "creds"));

        // When
        Optional<String> firstName = service.findCurrentUserFirstName();

        // Then
        assertThat(firstName).isEmpty();
    }

    @Test
    void findCurrentUserLastName_shouldReturnLastName_whenPrincipalIsAJwt() {
        // Given
        when(jwt.getClaimAsString("family_name")).thenReturn("Smith");
        authenticateWith(jwt);

        // When
        Optional<String> lastName = service.findCurrentUserLastName();

        // Then
        assertThat(lastName).contains("Smith");
    }

    @Test
    void findCurrentUserLastName_shouldBeEmpty_whenNoAuthentication() {
        // When
        Optional<String> lastName = service.findCurrentUserLastName();

        // Then
        assertThat(lastName).isEmpty();
    }

    @Test
    void findCurrentUserLastName_shouldBeEmpty_whenPrincipalIsNotAJwt() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("alice", "creds"));

        // When
        Optional<String> lastName = service.findCurrentUserLastName();

        // Then
        assertThat(lastName).isEmpty();
    }

    private void authenticateWith(Jwt principal) {
        Authentication authentication = new TestingAuthenticationToken(principal, "creds");
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
