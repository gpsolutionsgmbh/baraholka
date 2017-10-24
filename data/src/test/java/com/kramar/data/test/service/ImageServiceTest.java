package com.kramar.data.test.service;

import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.repository.ImageRepository;
import com.kramar.data.service.ImageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.kramar.data.test.utils.TestUtils.RANDOM_BYTE;
import static com.kramar.data.test.utils.TestUtils.createImage;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageServiceTest {

    @Autowired
    private ImageService imageService;
    @MockBean
    private ImageRepository imageRepository;

    private ImageDbo imageDbo;
    private List<ImageDbo> imageDbos;

    @Before
    public void setUp() {
        imageDbo = createImage();
        imageDbos = Arrays.asList(imageDbo, createImage(), createImage(), createImage());

        when(imageRepository.getById(any(UUID.class))).thenReturn(imageDbo);
        when(imageRepository.findAll(any(Iterable.class))).thenReturn(imageDbos);
        when(imageRepository.save(any(ImageDbo.class))).thenReturn(imageDbo);
    }

    @Test
    public void findByIdTest() {
        ImageDbo imageById = imageService.getImageById(imageDbo.getId());
        assertNotNull(imageById);
        assertTrue(imageById.getId().equals(imageDbo.getId()));
    }

    @Test
    public void findByIdsTest() {
        List<ImageDbo> imageByIds = imageService.getImageByIds(imageDbos.stream().map(ImageDbo::getId).collect(Collectors.toList()));
        assertNotNull(imageByIds);
        assertTrue(imageByIds.size() == imageDbos.size());
    }

    @Test
    public void saveImageTest() {
        MockMultipartFile file = new MockMultipartFile("file", "name.png", "image/png", RANDOM_BYTE);
        ImageDbo imageDbo = imageService.createImage(file);
        assertNotNull(imageDbo);
        imageDbo = imageService.saveImage(createImage());
        assertNotNull(imageDbo);
    }

    @Test
    public void deleteImageByIdTest() {
        imageService.deleteImageById(imageDbo.getId());
    }

}
