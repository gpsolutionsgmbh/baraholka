package com.kramar.security.audit;

import com.kramar.data.dbo.OAuthClient;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class CustomOAuthClientDetails implements ClientDetails {

    private static final long serialVersionUID = 705288988786456560L;

    private OAuthClient oAuthClient;
    private final static Integer TOKEN_TTL = -1;

    public CustomOAuthClientDetails(final OAuthClient oAuthClient) {
        Validate.notNull(oAuthClient, "oAuthClient can't be null");
        this.oAuthClient = oAuthClient;
    }

    @Override
    public String getClientId() {
        return this.oAuthClient.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return this.oAuthClient.getResources();
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return oAuthClient.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
//        return Collections.singleton("openid");
        return new HashSet<>(Arrays.asList("openid", "read", "write"));
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
//        return new HashSet<>(Arrays.asList("password", "refresh_token"));
        return new HashSet<>(Arrays.asList("password", "implicit", "refresh_token"));
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return TOKEN_TTL;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return TOKEN_TTL;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
