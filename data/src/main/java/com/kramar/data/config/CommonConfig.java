package com.kramar.data.config;

import com.kramar.data.service.SpringSecurityAuditorAware;
import com.kramar.data.util.AutowireHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.UUID;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.kramar")
public class CommonConfig {

    @Autowired
    private SpringSecurityAuditorAware springSecurityAuditorAware;

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return springSecurityAuditorAware;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AutowireHelper autowireHelper(){
        return AutowireHelper.getInstance();
    }

}
