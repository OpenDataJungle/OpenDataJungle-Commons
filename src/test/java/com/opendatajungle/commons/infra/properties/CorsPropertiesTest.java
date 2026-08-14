package com.opendatajungle.commons.infra.properties;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CorsPropertiesTest {

    @Test
    void gettersAndSetters_shouldRoundTripAllFields() {
        // Given
        CorsProperties properties = new CorsProperties();
        List<String> allowedOrigins = List.of("http://localhost");
        List<String> allowedMethods = List.of("GET", "POST");
        List<String> allowedHeaders = List.of("Authorization");
        List<String> exposedHeaders = List.of("X-Total-Count");

        // When
        properties.setAllowedOrigins(allowedOrigins);
        properties.setAllowedMethods(allowedMethods);
        properties.setAllowedHeaders(allowedHeaders);
        properties.setExposedHeaders(exposedHeaders);
        properties.setAllowCredentials(true);
        properties.setMaxAge(3600L);

        // Then
        assertThat(properties.getAllowedOrigins()).isEqualTo(allowedOrigins);
        assertThat(properties.getAllowedMethods()).isEqualTo(allowedMethods);
        assertThat(properties.getAllowedHeaders()).isEqualTo(allowedHeaders);
        assertThat(properties.getExposedHeaders()).isEqualTo(exposedHeaders);
        assertThat(properties.getAllowCredentials()).isTrue();
        assertThat(properties.getMaxAge()).isEqualTo(3600L);
    }
}
