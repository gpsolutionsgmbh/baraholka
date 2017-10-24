package com.kramar.data.repository;

import com.kramar.data.dbo.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OauthClientRepository extends JpaRepository<OAuthClient, UUID> {

    Optional<OAuthClient> findByClientId(final String clientId);

    default OAuthClient getByClientId(final String clientId) {
        return findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
    }
}
