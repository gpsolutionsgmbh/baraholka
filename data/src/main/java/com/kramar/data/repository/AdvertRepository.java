package com.kramar.data.repository;

import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.exception.ErrorReason;
import com.kramar.data.exception.ResourceNotFoundException;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.BooleanOperator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertRepository extends JpaRepository<AdvertDbo, UUID> {

    Page<AdvertDbo> findByOwner(final UserDbo user, final Pageable pageable);

    List<AdvertDbo> findByAdvertStatus(final AdvertStatus advertStatus);

    Optional<AdvertDbo> findById(final UUID id);

    Page<AdvertDbo> findByIdIn(List<UUID> ids, Pageable pageable);

    Optional<AdvertDbo> findByHeadLine(final String headLine);

    default AdvertDbo getById(final UUID id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorReason.RESOURCE_NOT_FOUND, "advert"));
    }

    /**
     * Full Text Search from PostgreSQL (https://www.postgresql.org/docs/9.3/static/textsearch-intro.html#TEXTSEARCH-DOCUMENT)
     *
     * @param text any text to search with separator - the Boolean operators & (AND), | (OR), and ! (NOT) (for example "car&blue")
     * @return Page adverts
     */
    @Query(value =
            "SELECT  " +
                    "  CAST(id AS varchar) as id, " +
                    "  advert_type, " +
                    "  advert_status, " +
                    "  headline, " +
                    "  price, " +
                    "  currency_type, " +
                    "  description, " +
                    "  CAST(user_id AS varchar) as user_id, " +
                    "  created_time, " +
                    "  created_by, " +
                    "  updated_time, " +
                    "  updated_by, " +
                    "  version " +
                    "FROM ( " +
                    "  SELECT  " +
                    "    id,  " +
                    "    advert_type, " +
                    "    advert_status, " +
                    "    headline, " +
                    "    price, " +
                    "    currency_type, " +
                    "    description, " +
                    "    user_id, " +
                    "    created_time, " +
                    "    created_by, " +
                    "    updated_time, " +
                    "    updated_by, " +
                    "    version, " +
                    "    to_tsvector(headline) || to_tsvector(description) AS document " +
                    "  FROM adverts) AS search " +
                    "WHERE search.document @@ to_tsquery(?1) " +
                    "ORDER BY ?#{#pageable}",
            countQuery =
                    "SELECT count(*) " +
                            "FROM ( " +
                            "  SELECT  " +
                            "    id,  " +
                            "    advert_type, " +
                            "    advert_status, " +
                            "    headline, " +
                            "    price, " +
                            "    currency_type, " +
                            "    description, " +
                            "    user_id, " +
                            "    created_time, " +
                            "    created_by, " +
                            "    updated_time, " +
                            "    updated_by, " +
                            "    version, " +
                            "    to_tsvector(headline) || to_tsvector(description) AS document " +
                            "  FROM adverts) AS search " +
                            "WHERE search.document @@ to_tsquery(?1)",
            nativeQuery = true)
    Page<AdvertDbo> findByTextInTitleOrInDescriptionNativeQuery(final String text, final Pageable pageable);

}
