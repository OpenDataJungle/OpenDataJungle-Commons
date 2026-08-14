package com.opendatajungle.commons.infra.properties;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpClientPropertiesTest {

    @Test
    void gettersAndSetters_shouldRoundTripAllFields() {
        // Given
        HttpClientProperties properties = new HttpClientProperties();

        // When
        properties.setConnectTimeoutSeconds(5);
        properties.setReadTimeoutSeconds(10);

        // Then
        assertThat(properties.getConnectTimeoutSeconds()).isEqualTo(5);
        assertThat(properties.getReadTimeoutSeconds()).isEqualTo(10);
    }
}
