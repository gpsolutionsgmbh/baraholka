package com.kramar.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kramar.data.type.ImageType;
import lombok.*;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(NON_NULL)
public class ImageDto extends AbstractDto {

    @Builder
    public ImageDto(ImageType imageType, UUID id) {
        this.id = id;
        this.imageType = imageType;
    }

    @NonNull
    private ImageType imageType;
}
