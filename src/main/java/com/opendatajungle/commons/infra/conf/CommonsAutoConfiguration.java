package com.opendatajungle.commons.infra.conf;

import com.opendatajungle.commons.business.service.AuthenticationUseCase;
import com.opendatajungle.commons.infra.conf.mdc.MdcFilter;
import com.opendatajungle.commons.infra.service.LocalAuthenticationService;
import com.opendatajungle.commons.infra.service.SecurityContextAuthenticationService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@AutoConfiguration
public class CommonsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AuthenticationUseCase.class)
    @Profile("local")
    public AuthenticationUseCase localAuthenticationUseCase() {
        return new LocalAuthenticationService();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationUseCase.class)
    @Profile("!local")
    public AuthenticationUseCase authenticationUseCase() {
        return new SecurityContextAuthenticationService();
    }

    @Bean
    @ConditionalOnMissingBean(MdcFilter.class)
    public MdcFilter mdcFilter() {
        return new MdcFilter();
    }
}
