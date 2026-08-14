package com.opendatajungle.commons.business.exception;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParamExceptionTest {

    @Test
    void constructor_shouldExposeCodeFieldMessageAndInformation() {
        // Given
        Map<String, String> information = Map.of("hint", "provide a name");

        // When
        ParamException exception = new ParamException("REQUIRED", "name is required", "name", information);

        // Then
        assertThat(exception.getCode()).isEqualTo("REQUIRED");
        assertThat(exception.getField()).isEqualTo("name");
        assertThat(exception.getMessage()).isEqualTo("name is required");
        assertThat(exception.getInformation()).isEqualTo(information);
    }

    @Test
    void constructor_shouldDefaultInformationToEmptyMap_whenNotProvided() {
        // When
        ParamException exception = new ParamException("REQUIRED", "name is required", "name");

        // Then
        assertThat(exception.getInformation()).isEmpty();
    }
}
