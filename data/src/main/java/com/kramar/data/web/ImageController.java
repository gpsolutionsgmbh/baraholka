package com.kramar.data.web;

import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.service.ImageService;
import com.kramar.data.type.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping(ImageController.REQUEST_MAPPING)
public class ImageController {
    public static final String REQUEST_MAPPING = "/images";

    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") final UUID id) {
        final ImageDbo image = imageService.getImageById(id);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(image.getMimeType()));
        return new ResponseEntity<>(image.getContent(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> saveImage(@RequestParam final MultipartFile file, @RequestParam final ImageType imageType) {
        return new ResponseEntity<>(imageService.saveImage(file, imageType), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
