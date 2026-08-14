package com.opendatajungle.commons.client.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralResponseExceptionTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void accessors_shouldExposeConstructorValues() {
        // Given
        Map<String, String> information = Map.of("hint", "provide a name");

        // When
        GeneralResponseException response = new GeneralResponseException(
                "REQUIRED", "name is required", "/api/v1/resources", "name", information);

        // Then
        assertThat(response.code()).isEqualTo("REQUIRED");
        assertThat(response.message()).isEqualTo("name is required");
        assertThat(response.path()).isEqualTo("/api/v1/resources");
        assertThat(response.field()).isEqualTo("name");
        assertThat(response.information()).isEqualTo(information);
    }

    @Test
    void serialize_shouldOmitNullFields() throws Exception {
        // Given
        GeneralResponseException response = new GeneralResponseException(
                "NOT_FOUND", "not found", "/api/v1/resources", null, null);

        // When
        String json = objectMapper.writeValueAsString(response);

        // Then
        assertThat(json)
                .contains("\"code\":\"NOT_FOUND\"")
                .contains("\"message\":\"not found\"")
                .contains("\"path\":\"/api/v1/resources\"")
                .doesNotContain("\"field\"")
                .doesNotContain("\"information\"");
    }
}
