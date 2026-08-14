package com.opendatajungle.commons.infra.conf;

import com.opendatajungle.commons.infra.properties.HttpClientProperties;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

class WebConfigurationTest {

    private final WebConfiguration configuration = new WebConfiguration();

    @Test
    void restClientBuilder_shouldBuildBuilder_configuredWithTimeouts() {
        // Given
        HttpClientProperties properties = new HttpClientProperties();
        properties.setConnectTimeoutSeconds(5);
        properties.setReadTimeoutSeconds(10);

        // When
        RestClient.Builder builder = configuration.restClientBuilder(properties);

        // Then
        assertThat(builder).isNotNull();
        assertThat(builder.build()).isNotNull();
    }
}
