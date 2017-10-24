package com.kramar.data.repository;

import com.kramar.data.dbo.UserDbo;
import com.kramar.data.exception.ErrorReason;
import com.kramar.data.exception.ForbiddenException;
import com.kramar.data.exception.ResourceNotFoundException;
import com.kramar.data.type.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDbo, UUID> {

    Optional<UserDbo> findByEmail(final String email);

    default UserDbo getUserByEmail(final String email) {
        final Optional<UserDbo> userByEmailOpt = findByEmail(email);
        if (userByEmailOpt.isPresent()) {
            final UserDbo user = userByEmailOpt.get();
            if (UserStatus.ACTIVE.equals(user.getStatus())) {
                return user;
            }
            throw new ForbiddenException(ErrorReason.BLOCKED_ACCOUNT);
        }
        final String msg = String.format(ErrorReason.USER_NOT_FOUND.getDescription(), "email", email);
        throw new UsernameNotFoundException(msg);
    }

    Page<UserDbo> findAll(final Pageable pageable);

    Optional<UserDbo> findById(final UUID id);

    default UserDbo getById(final UUID id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorReason.RESOURCE_NOT_FOUND, "user"));
    }

}
