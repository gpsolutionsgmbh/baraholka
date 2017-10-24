package com.kramar.data.test.repository;

import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.exception.ResourceNotFoundException;
import com.kramar.data.repository.ImageRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
import java.util.UUID;

import static com.kramar.data.test.utils.TestUtils.createImage;
import static junit.framework.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    private static final UUID INVALID_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private ImageDbo imageDbo;

    @Before
    public void setUp() {
        imageDbo = createImage();
        imageDbo = imageRepository.save(imageDbo);
    }

    @After
    public void del() {
        imageRepository.delete(imageDbo.getId());
    }

    @Test
    public void findByIdTest() {
        Optional<ImageDbo> byId = imageRepository.findById(imageDbo.getId());
        assertTrue(byId.isPresent());
        assertTrue(byId.get().getId().equals(imageDbo.getId()));

        byId = imageRepository.findById(INVALID_ID);
        assertFalse(byId.isPresent());
    }

    @Test
    public void getByIdTest() {
        ImageDbo byId = imageRepository.getById(imageDbo.getId());
        assertNotNull(byId);
        assertTrue(byId.getId().equals(imageDbo.getId()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByIdWithExceptionTest() {
        imageRepository.getById(INVALID_ID);
    }

}
