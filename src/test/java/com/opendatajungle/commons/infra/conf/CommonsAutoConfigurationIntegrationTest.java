package com.opendatajungle.commons.infra.conf;

import com.opendatajungle.commons.TestApplication;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CommonsAutoConfigurationIntegrationTest {

    @Nested
    @SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @Import(JwtDecoderTestConfig.class)
    class ProductionProfile {
        @Test
        void applicationStarts(ApplicationContext context) {
            assertThat(context).isNotNull();
        }
    }

    @Nested
    @SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @ActiveProfiles("test")
    class TestProfile {
        @Test
        void applicationStarts(ApplicationContext context) {
            assertThat(context).isNotNull();
        }
    }

    @TestConfiguration
    static class JwtDecoderTestConfig {
        @Bean
        JwtDecoder jwtDecoder() {
            return mock(JwtDecoder.class);
        }
    }
}
