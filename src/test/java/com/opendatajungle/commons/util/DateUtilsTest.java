package com.opendatajungle.commons.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    void now_shouldReturnCurrentTimestamp() {
        // Given
        Instant before = Instant.now().minus(Duration.ofSeconds(1));

        // When
        Instant result = DateUtils.now();

        // Then
        Instant after = Instant.now().plus(Duration.ofSeconds(1));
        assertThat(result).isBetween(before, after);
    }
}
