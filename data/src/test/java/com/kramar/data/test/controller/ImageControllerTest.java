package com.kramar.data.test.controller;

import com.kramar.data.DataApplication;
import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.service.ImageService;
import com.kramar.data.type.ImageType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.kramar.data.test.utils.TestUtils.*;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataApplication.class)
@WebAppConfiguration
public class ImageControllerTest {

    private MockMvc restMvc;

    @Autowired
    private WebApplicationContext webapp;
    @MockBean
    private ImageService imageService;

    @Before
    public void setup() {
        restMvc = MockMvcBuilders.webAppContextSetup(webapp).build();
        final ImageDbo imageDbo = createImage();

        when(imageService.getImageById(any(UUID.class))).thenReturn(imageDbo);
        when(imageService.saveImage(any(MultipartFile.class), any(ImageType.class))).thenReturn(ID);
        doNothing().when(imageService).deleteImageById(any(UUID.class));
    }

    @Test
    public void badRequestTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(fileUpload("/images")).andDo(print());
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void addImageTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "name.png", "image/png", RANDOM_BYTE);
        final ResultActions resultActions = restMvc.perform(
                fileUpload("/images")
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("imageType", "COMMON"))
                .andDo(print());

        assertTrue(resultActions
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString().contains(ID.toString()));
    }

    @Test
    public void getImageByIdTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(
                get("/images/" + ID.toString()).contentType(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(RANDOM_BYTE));
    }

    @Test
    public void deleteImageTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(
                delete("/images/" + ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions.andExpect(status().isNoContent());
    }
}
