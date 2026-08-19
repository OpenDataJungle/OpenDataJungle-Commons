package com.opendatajungle.commons.infra.conf.security;

import com.opendatajungle.commons.infra.conf.mdc.MdcUserFilter;
import com.opendatajungle.commons.infra.properties.CorsProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigurationTest {

    private CorsProperties corsProperties;
    private SecurityConfiguration configuration;

    @BeforeEach
    void setUp() {
        corsProperties = new CorsProperties();
        corsProperties.setAllowedOrigins(List.of("http://localhost"));
        corsProperties.setAllowedMethods(List.of("GET"));
        corsProperties.setAllowedHeaders(List.of("Authorization"));
        corsProperties.setExposedHeaders(List.of("X-Total-Count"));
        corsProperties.setAllowCredentials(true);
        corsProperties.setMaxAge(3600L);

        configuration = new SecurityConfiguration(corsProperties);
    }

    @Test
    void jwtAuthenticationConverter_shouldUseScopeClaim() {
        // Given
        JwtAuthenticationConverter converter = configuration.jwtAuthenticationConverter();
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("scope", "resources:read resources:write")
                .subject("user")
                .build();

        // When
        var authorities = converter.convert(jwt).getAuthorities();

        // Then
        assertThat(authorities)
                .extracting(GrantedAuthority::getAuthority)
                .contains("resources:read", "resources:write");
    }

    @Test
    void corsConfigurationSource_shouldExposeConfiguredCorsProperties() {
        // When
        CorsConfigurationSource source = configuration.corsConfigurationSource();
        CorsConfiguration corsConfiguration = source.getCorsConfiguration(new MockHttpServletRequest());

        // Then
        assertThat(corsConfiguration.getAllowedOrigins()).containsExactly("http://localhost");
        assertThat(corsConfiguration.getAllowedMethods()).containsExactly("GET");
        assertThat(corsConfiguration.getAllowedHeaders()).containsExactly("Authorization");
        assertThat(corsConfiguration.getExposedHeaders()).containsExactly("X-Total-Count");
        assertThat(corsConfiguration.getAllowCredentials()).isTrue();
        assertThat(corsConfiguration.getMaxAge()).isEqualTo(3600L);
    }

    @Test
    void securityFilterChain_shouldBuildChain_andRegisterMdcUserFilter() {
        // Given
        HttpSecurity http = mock(HttpSecurity.class, Answers.RETURNS_SELF);
        DefaultSecurityFilterChain builtChain = mock(DefaultSecurityFilterChain.class);
        when(http.build()).thenReturn(builtChain);

        // When
        SecurityFilterChain result = configuration.securityFilterChain(http, mock(SecurityExceptionHandler.class), mock(CorsConfigurationSource.class));

        // Then
        assertThat(result).isSameAs(builtChain);
        verify(http).addFilterAfter(any(MdcUserFilter.class), eq(BearerTokenAuthenticationFilter.class));
    }
}
