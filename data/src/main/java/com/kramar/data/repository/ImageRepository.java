package com.kramar.data.repository;

import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.exception.ErrorReason;
import com.kramar.data.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<ImageDbo, UUID> {

    Optional<ImageDbo> findById(final UUID id);

    default ImageDbo getById(final UUID id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorReason.RESOURCE_NOT_FOUND, "image"));
    }
}
