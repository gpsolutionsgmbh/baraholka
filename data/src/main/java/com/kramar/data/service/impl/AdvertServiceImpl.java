package com.kramar.data.service.impl;

import com.kramar.data.converter.AdvertConverter;
import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.profiling.Profiling;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.service.AdvertService;
import com.kramar.data.service.AuthenticationService;
import com.kramar.data.dto.AdvertDto;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.BooleanOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service layer class to wort with adverts
 */
@Service
@Profiling
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private AdvertConverter advertConverter;
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Get all adverts with pagination
     * @param pageable class with pagination information
     * @return advert with pagination
     */
    @Override
    public Page<AdvertDto> getAllAdverts(final Pageable pageable) {
        return advertRepository.findAll(pageable).map(advertConverter::transform);
    }

    /**
     * Get all adverts by status
     * @param advertStatus advert status
     * @return list adverts
     */
    @Override
    public List<AdvertDto> getAllAdvertsByStatus(final AdvertStatus advertStatus) {
        final List<AdvertDbo> byAdvertStatus = advertRepository.findByAdvertStatus(advertStatus);
        return byAdvertStatus.stream().map(advertConverter::transform).collect(Collectors.toList());
    }

    /** Get all adverts with pagination by current user in session
     * @param pageable class with pagination information
     * @return advert with pagination
     */
    @Override
    public Page<AdvertDto> getAllAdvertsByUser(final Pageable pageable) {
        final UserDbo user = authenticationService.getCurrentUser();
        return advertRepository.findByOwner(user, pageable).map(advertConverter::transform);
    }

    /** Create new advert
     * @param advertDto new advert
     * @return saved advert
     */
    @Override
    public AdvertDto createAdvert(final AdvertDto advertDto) {
        advertDto.setId(null);
        AdvertDbo advertDbo = advertConverter.transform(advertDto);
        advertDbo = advertRepository.save(advertDbo);
        return advertConverter.transform(advertDbo);
    }

    /**
     * Get advert by id
     * @param id advert id
     * @return advert
     */
    @Override
    public AdvertDto getAdvertById(final UUID id) {
        final AdvertDbo advertDbo = advertRepository.getById(id);
        return advertConverter.transform(advertDbo);
    }

    /** Delete advert by id
     * @param id advert id
     */
    @Override
    public void deleteAdvertById(final UUID id) {
        final AdvertDbo advertDbo = advertRepository.getById(id);
        advertRepository.delete(advertDbo);
    }

    /**
     * Modify advert
     * @param id advert id
     * @param advertDto advert to modify
     * @return modified advert
     */
    @Override
    public AdvertDto modifyAdvertById(final UUID id, final AdvertDto advertDto) {
        final AdvertDbo oldAdvert = advertRepository.getById(id);
        advertDto.setId(oldAdvert.getId());
        AdvertDbo advertDbo = advertConverter.transform(advertDto);
        advertDbo = advertRepository.save(advertDbo);
        return advertConverter.transform(advertDbo);
    }

    /** Full Text Search
     * @param searchText any searchText to search (for example "blue car")
     * @param booleanOperator the Boolean operator AND/OR to search all words or any existing word
     * @param pageable class with pagination information
     * @return Page adverts
     */
    @Override
    public Page<AdvertDto> getAdvertsByTextInTitleOrInDescription(final String searchText, final BooleanOperator booleanOperator, final Pageable pageable) {
        final String splittedText =
                booleanOperator.equals(BooleanOperator.AND)
                ? searchText.replace(" ", "&")
                : searchText.replace(" ", "|");

        return advertRepository
                .findByTextInTitleOrInDescriptionNativeQuery(splittedText, pageable)
                .map(advertConverter::transform);
    }
}
