package com.opendatajungle.commons.infra.conf.security;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WithoutSecurityConfigurationTest {

    private final WithoutSecurityConfiguration configuration = new WithoutSecurityConfiguration();

    @Test
    void permissiveCorsConfigurationSource_shouldAllowAnyOriginMethodAndHeader() {
        // When
        CorsConfigurationSource source = configuration.corsConfigurationSource();
        CorsConfiguration corsConfiguration = source.getCorsConfiguration(new MockHttpServletRequest());

        // Then
        assertThat(corsConfiguration.getAllowedOriginPatterns()).containsExactly("*");
        assertThat(corsConfiguration.getAllowedMethods()).containsExactly("*");
        assertThat(corsConfiguration.getAllowedHeaders()).containsExactly("*");
        assertThat(corsConfiguration.getAllowCredentials()).isTrue();
    }

    @Test
    void testSecurityFilterChain_shouldReturnBuiltChain() {
        // Given
        HttpSecurity http = mock(HttpSecurity.class, Answers.RETURNS_SELF);
        DefaultSecurityFilterChain builtChain = mock(DefaultSecurityFilterChain.class);
        when(http.build()).thenReturn(builtChain);

        // When
        SecurityFilterChain result = configuration.testSecurityFilterChain(http, mock(CorsConfigurationSource.class));

        // Then
        assertThat(result).isSameAs(builtChain);
    }
}
