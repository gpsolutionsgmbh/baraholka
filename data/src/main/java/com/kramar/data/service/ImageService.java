package com.kramar.data.service;

import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.type.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ImageService {

    List<UUID> getAllImagesByAdvertId(final UUID id);

    ImageDbo createImage(final MultipartFile file);

    ImageDbo getImageById(final UUID id);

    List<ImageDbo> getImageByIds(final Collection<UUID> ids);

    ImageDbo saveImage(final ImageDbo imageDbo);

    UUID saveImage(final MultipartFile image, final ImageType imageType);

    void deleteImageById(final UUID id);

}
