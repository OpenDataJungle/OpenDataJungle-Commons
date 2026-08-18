package com.opendatajungle.commons.util;

import java.time.Instant;

public class DateUtils {
    private DateUtils() {
    }

    public static Instant now() {
        return Instant.now();
    }
}
