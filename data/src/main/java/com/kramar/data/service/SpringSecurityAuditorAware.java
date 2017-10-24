package com.kramar.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<UUID> {

    @Autowired
    private AuthenticationService authService;

    @Override
    public UUID getCurrentAuditor() {
        return authService.getCurrentUserId();
    }
}
