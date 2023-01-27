package com.opencbs.genesis.configs;

import com.opencbs.genesis.aspects.ServiceValidationAspect;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.properties.AttachmentProperties;
import com.opencbs.genesis.properties.GeneralProperties;
import com.opencbs.genesis.security.AuthInterceptor;
import com.opencbs.genesis.security.PrincipalInitializationFilter;
import com.opencbs.genesis.security.UserAuditorAwareImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Makhsut Islamov on 26.10.2016.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableJpaAuditing
@EnableScheduling
@EnableConfigurationProperties({AttachmentProperties.class, GeneralProperties.class})
public class GenesisConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new PrincipalInitializationFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    @Bean
    public AuthInterceptor getAuthInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<User> auditorProvider() {
        return new UserAuditorAwareImpl();
    }

    @Bean
    public ServiceValidationAspect serviceValidationAspect(){
        return new ServiceValidationAspect();
    }
}
