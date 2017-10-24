package com.kramar.data.service;

import com.kramar.data.dto.AdvertDto;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.BooleanOperator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AdvertService {

    Page<AdvertDto> getAllAdverts(final Pageable pageable);

    List<AdvertDto> getAllAdvertsByStatus(final AdvertStatus advertStatus);

    Page<AdvertDto> getAllAdvertsByUser(final Pageable pageable);

    AdvertDto createAdvert(final AdvertDto advertDto);

    AdvertDto getAdvertById(final UUID id);

    void deleteAdvertById(final UUID id);

    AdvertDto modifyAdvertById(final UUID id, final AdvertDto advertDto);

    Page<AdvertDto> getAdvertsByTextInTitleOrInDescription(String text, BooleanOperator booleanOperator, Pageable pageable);

}
