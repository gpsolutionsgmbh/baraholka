package com.kramar.data.test.controller;

import com.kramar.data.DataApplication;
import com.kramar.data.converter.AdvertConverter;
import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.AdvertDto;
import com.kramar.data.service.AdvertService;
import com.kramar.data.test.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
public class AdvertControllerTest {

    private AdvertDbo advertDbo;
    private UserDbo userDbo;
    private List<AdvertDbo> advertDbos;
    private List<AdvertDto> advertDtos;
    private MockMvc restMvc;

    private static final String ADVERT_OBJECT =
            "{" +
                    "\"advertType\": \"SALE\",\n" +
                    "\"advertStatus\": \"ACTIVE\",\n" +
                    "\"headLine\": \"First Advert2\",\n" +
                    "\"price\": 99,\n" +
                    "\"currencyType\": \"USD\",\n" +
                    "\"description\": \"first ad in app\"\n" +
                    "}";

    @Autowired
    private WebApplicationContext webapp;
    @MockBean
    private AdvertService advertService;
    @Autowired
    private AdvertConverter advertConverter;

    @Before
    public void setup() {
        restMvc = MockMvcBuilders.webAppContextSetup(webapp).build();
        userDbo = TestUtils.createUser();
        advertDbo = TestUtils.createAdvert(userDbo);
        advertDbos = Arrays.asList(advertDbo, TestUtils.createAdvert(userDbo), TestUtils.createAdvert(userDbo), TestUtils.createAdvert(userDbo));
        advertDtos = advertDbos.stream().map(advertConverter::transform).collect(Collectors.toList());

        when(advertService.getAllAdverts(any(Pageable.class))).thenReturn(new PageImpl(advertDtos));
        when(advertService.getAdvertById(any(UUID.class))).thenReturn(advertDtos.get(0));
        when(advertService.createAdvert(any(AdvertDto.class))).thenReturn(advertDtos.get(0));
        when(advertService.modifyAdvertById(any(UUID.class), any(AdvertDto.class))).thenReturn(advertDtos.get(0));
        doNothing().when(advertService).deleteAdvertById(any(UUID.class));
    }

    @Test
    public void badRequestTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(post("/adverts")).andDo(print());
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void getAllAdvertsTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(
                get("/adverts").contentType(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty());

        final String content = resultActions.andReturn().getResponse().getContentAsString();
        assertTrue(content.contains(TestUtils.ID.toString()));
    }

    @Test
    public void getAdvertByIdTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(
                get("/adverts/" + TestUtils.ID.toString()).contentType(MediaType.APPLICATION_JSON)).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TestUtils.ID.toString()));
    }

    @Test
    public void addAdvertTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(
                post("/adverts/")
                        .content(ADVERT_OBJECT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TestUtils.ID.toString()));
    }

    @Test
    public void updateAdvertTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(
                put("/adverts/" + TestUtils.ID.toString())
                        .content(ADVERT_OBJECT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TestUtils.ID.toString()));
    }

    @Test
    public void deleteAdvertTest() throws Exception {
        final ResultActions resultActions = restMvc.perform(
                delete("/adverts/" + TestUtils.ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions.andExpect(status().isNoContent());
    }
}
