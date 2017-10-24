package com.kramar.data.web;

import com.kramar.data.dto.AdvertDto;
import com.kramar.data.service.AdvertService;
import com.kramar.data.type.BooleanOperator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(AdvertController.REQUEST_MAPPING)
public class AdvertController {

    public static final String REQUEST_MAPPING = "/adverts";

    @Autowired
    private AdvertService advertService;

    /**
     * Get all adverts with pagination
     *
     * @param pageable class with pagination information
     * @return advert with pagination
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "List all adverts",
            notes = "List all adverts using paging",
            response = AdvertDto.class,
            responseContainer = "List"
    )
    public Page<AdvertDto> getAllAdverts(final Pageable pageable) {
        return advertService.getAllAdverts(pageable);
    }

    /**
     * Get advert by id
     *
     * @param id advert id
     * @return advert
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "Find advert by id",
            notes = "Find advert by id (UUID)",
            response = AdvertDto.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Advert with such ID", response = AdvertDto.class),
            @ApiResponse(code = 404, message = "Advert with such ID doesn't exists")
    })
    public AdvertDto getAdvert(@PathVariable("id") final UUID id) {
        return advertService.getAdvertById(id);
    }

    /**
     * Create new advert
     *
     * @param advertDto new advert
     * @return saved advert
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
            value = "Save new advert",
            notes = "Save new advert",
            response = AdvertDto.class
    )
    @ApiResponses({
            @ApiResponse(code = 201, message = "Save advert", response = AdvertDto.class),
    })
    public AdvertDto createAdvert(@RequestBody @Validated final AdvertDto advertDto) {
        return advertService.createAdvert(advertDto);
    }

    /**
     * Modify advert
     *
     * @param id        advert id
     * @param advertDto advert to modify
     * @return modified advert
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "Modify advert",
            notes = "Modify advert",
            response = AdvertDto.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Modify advert", response = AdvertDto.class),
    })
    public AdvertDto modifyAdvert(@PathVariable("id") final UUID id, @RequestBody @Validated final AdvertDto advertDto) {
        return advertService.modifyAdvertById(id, advertDto);
    }

    /**
     * Delete advert by id
     *
     * @param id advert id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(
            value = "Delete advert",
            notes = "Delete advert",
            response = AdvertDto.class
    )
    @ApiResponses({
            @ApiResponse(code = 204, message = "Delete advert", response = AdvertDto.class),
    })
    public void deleteAdvert(@PathVariable("id") final UUID id) {
        advertService.deleteAdvertById(id);
    }

    /**
     * Full Text Search
     *
     * @param searchText      any searchText to search (for example "blue car")
     * @param booleanOperator the Boolean operator AND/OR to search all words or any existing word
     * @param pageable        class with pagination information
     * @return Page adverts
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "Search adverts",
            notes = "Search adverts by text in title and description of advert using paging. " +
                    "Any text to search (for example \"blue car\"). " +
                    "Boolean operator AND/OR to search all words or any existing word",
            response = AdvertDto.class,
            responseContainer = "List"
    )
    public Page<AdvertDto> getAdvertsByTextInTitleOrInDescription(@RequestParam("text") final String searchText,
                                                                  @RequestParam("operator") final BooleanOperator booleanOperator,
                                                                  final Pageable pageable) {
        return advertService.getAdvertsByTextInTitleOrInDescription(searchText, booleanOperator, pageable);
    }

}
