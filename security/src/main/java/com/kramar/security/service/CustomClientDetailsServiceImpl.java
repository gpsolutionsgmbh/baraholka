package com.kramar.security.service;

import com.kramar.data.dbo.OAuthClient;
import com.kramar.data.repository.OauthClientRepository;
import com.kramar.security.audit.CustomOAuthClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomClientDetailsServiceImpl implements CustomClientDetailsService {

    @Autowired
    private OauthClientRepository oauthClientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        final OAuthClient oAuthClient = oauthClientRepository.getByClientId(clientId);
        return new CustomOAuthClientDetails(oAuthClient);
    }
}