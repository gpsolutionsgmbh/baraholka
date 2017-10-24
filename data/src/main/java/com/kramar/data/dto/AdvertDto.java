package com.kramar.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.AdvertType;
import com.kramar.data.type.CurrencyType;
import com.kramar.data.type.ImageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(NON_NULL)
@ApiModel(value = "Advert")
public class AdvertDto extends AbstractDto {

    @NotNull
    @ApiModelProperty(value = "Advert type", allowableValues = "BUY, SALE, SERVICE", required = true)
    private AdvertType advertType;

    @NotNull
    @ApiModelProperty(value = "Advert status", allowableValues = "ACTIVE, INACTIVE, DELETED", required = true)
    private AdvertStatus advertStatus;

    @Length(max = 50)
    @NotBlank
    @ApiModelProperty(value = "Advert headline", required = true)
    private String headLine;

    @Digits(integer = 10, fraction = 2)
    @ApiModelProperty(value = "Advert price")
    private BigDecimal price;

    @NotNull
    @ApiModelProperty(value = "Advert price currency", allowableValues = "BYN, RUB, EUR, USD")
    private CurrencyType currencyType;

    @Length(max = 4000)
    @ApiModelProperty(value = "Advert description")
    private String description;

    @ApiModelProperty(value = "Advert main image (UUID)")
    private UUID headLineImage;

    @ApiModelProperty(value = "Advert images")
    private List<ImageDto> images;

}