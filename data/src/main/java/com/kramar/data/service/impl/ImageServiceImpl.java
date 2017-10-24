package com.kramar.data.service.impl;

import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.exception.BadRequestException;
import com.kramar.data.exception.ErrorReason;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.repository.ImageRepository;
import com.kramar.data.service.ImageService;
import com.kramar.data.type.ImageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private static final List<String> VALID_MIME_TYPES = Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE);

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AdvertRepository advertRepository;

    private void validateFileType(final MultipartFile file) {
        if (file == null || StringUtils.isEmpty(file.getContentType()) ||
                !CollectionUtils.contains(VALID_MIME_TYPES.iterator(), file.getContentType())) {
            throw new BadRequestException(ErrorReason.INVALID_UPLOAD_FILE_TYPE, String.join(",", VALID_MIME_TYPES));
        }
    }

    private byte[] convertFile(final MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            log.error("Unable to convert Multipart file to byte array", e);
            throw new BadRequestException(ErrorReason.UNABLE_TO_CONVERT_IMAGE);
        }
    }

    /** Create new image
     * @param image image in a representation of an uploaded file received in a multipart request
     * @return image
     */
    public ImageDbo createImage(final MultipartFile image) {
        validateFileType(image);
        return new ImageDbo(image.getContentType(), convertFile(image));
    }

    /** Get image by id
     * @param id image id
     * @return image
     */
    public ImageDbo getImageById(final UUID id) {
        return imageRepository.getById(id);
    }

    /** Get image list by images id
     * @param ids images id
     * @return list of images
     */
    public List<ImageDbo> getImageByIds(final Collection<UUID> ids) {
        return imageRepository.findAll(ids);
    }

    /** Save image
     * @param imageDbo image
     * @return saved image
     */
    public ImageDbo saveImage(final ImageDbo imageDbo) {
        return imageRepository.save(imageDbo);
    }

    /** Save image
     * @param image image in a representation of an uploaded file received in a multipart request
     * @param imageType image type
     * @return image id
     */
    public UUID saveImage(final MultipartFile image, final ImageType imageType) {
        final ImageDbo imageDbo = createImage(image);
        imageDbo.setImageType(imageType);
        return imageRepository.save(imageDbo).getId();
    }

    /** Delete image
     * @param id image id
     */
    public void deleteImageById(final UUID id) {
        imageRepository.delete(id);
    }

    /** Get all images for advert
     * @param id advert id
     * @return list images UUID
     */
    public List<UUID> getAllImagesByAdvertId (final UUID id) {
        List<ImageDbo> byAdvert = advertRepository.findOne(id).getImages();
        return byAdvert.stream().map(ImageDbo::getId).collect(Collectors.toList());
    }

}
