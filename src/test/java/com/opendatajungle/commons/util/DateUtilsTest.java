package com.opendatajungle.commons.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    void now_shouldReturnCurrentDateTime_inUtc() {
        // Given
        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);

        // When
        LocalDateTime result = DateUtils.now();

        // Then
        LocalDateTime after = LocalDateTime.now(ZoneOffset.UTC);
        assertThat(result).isBetween(before.minus(Duration.ofSeconds(100)), after.plus(Duration.ofSeconds(100)));
    }
}
