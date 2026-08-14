package com.opendatajungle.commons.business.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotFoundExceptionTest {

    @Test
    void constructor_shouldBuildMessage_fromResourceTypeAndId() {
        // When
        NotFoundException exception = new NotFoundException("Resource", "123");

        // Then
        assertThat(exception.getMessage()).isEqualTo("Resource not found with id: 123");
    }
}
