package com.opendatajungle.commons.infra.conf;

import com.opendatajungle.commons.infra.properties.CorsProperties;
import com.opendatajungle.commons.infra.properties.HttpClientProperties;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@AutoConfiguration
@EnableConfigurationProperties({HttpClientProperties.class, CorsProperties.class})
public class WebConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestClient.Builder restClientBuilder(HttpClientProperties httpClientProperties) {
        HttpClientSettings settings = HttpClientSettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(httpClientProperties.getConnectTimeoutSeconds()))
                .withReadTimeout(Duration.ofSeconds(httpClientProperties.getReadTimeoutSeconds()));

        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.detect().build(settings);

        return RestClient.builder().requestFactory(requestFactory);
    }
}
