package com.kramar.data.repository;

import com.kramar.data.dbo.AdvertHistoryDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdvertHistoryRepository extends JpaRepository<AdvertHistoryDbo, UUID> {

    List<AdvertHistoryDbo> findByOwner(UUID id);

}
