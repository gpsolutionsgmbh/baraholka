package com.kramar.data.converter;

import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.dto.ImageDto;
import com.kramar.data.service.AuthenticationService;
import com.kramar.data.dto.AdvertDto;
import com.kramar.data.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@Component
public class AdvertConverter extends AbstractDtoConverter<AdvertDbo, AdvertDto> {

    @Autowired
    private ImageService imageService;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    AdvertDto createNewDto() {
        return new AdvertDto();
    }

    @Override
    AdvertDbo createNewEntity() {
        return new AdvertDbo();
    }

    @Override
    void convertFromEntity(final AdvertDbo entity, final AdvertDto dto) {
        dto.setId(entity.getId());
        dto.setAdvertType(entity.getAdvertType());
        dto.setAdvertStatus(entity.getAdvertStatus());
        dto.setHeadLine(entity.getHeadLine());
        dto.setPrice(entity.getPrice());
        dto.setCurrencyType(entity.getCurrencyType());
        dto.setDescription(entity.getDescription());
        dto.setImages(!CollectionUtils.isEmpty(entity.getImages()) ?
                entity.getImages().stream().map(i -> new ImageDto(i.getImageType(), i.getId())).collect(Collectors.toList())
                : null);
    }

    @Override
    void convertFromDto(final AdvertDto dto, final AdvertDbo entity) {
        entity.setAdvertType(dto.getAdvertType());
        entity.setAdvertStatus(dto.getAdvertStatus());
        entity.setHeadLine(dto.getHeadLine());
        entity.setPrice(dto.getPrice());
        entity.setCurrencyType(dto.getCurrencyType());
        entity.setDescription(dto.getDescription());
        entity.setImages(imageService.getImageByIds(dto.getImages().stream().map(ImageDto::getId).collect(Collectors.toList())));
        entity.setOwner(authenticationService.getCurrentUser());
    }
}