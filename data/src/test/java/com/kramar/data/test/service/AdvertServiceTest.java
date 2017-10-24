package com.kramar.data.test.service;

import com.kramar.data.converter.AdvertConverter;
import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.AdvertDto;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.repository.UserRepository;
import com.kramar.data.service.AdvertService;
import com.kramar.data.service.AuthenticationService;
import com.kramar.data.test.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdvertServiceTest {

    @Autowired
    private AdvertService advertService;
    @Autowired
    private AdvertConverter advertConverter;
    @MockBean
    private AdvertRepository advertRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthenticationService authenticationService;

    private AdvertDbo advertDbo;
    private UserDbo userDbo;
    private List<AdvertDbo> advertDbos;
    private List<AdvertDto> advertDtos;
    private Pageable pageRequest;

    @Before
    public void setUp() {
        final Sort sort = new Sort(Sort.Direction.ASC, "name");
        pageRequest = new PageRequest(1, 1, sort);
        userDbo = TestUtils.createUser();
        advertDbo = TestUtils.createAdvert(userDbo);
        advertDbos = Arrays.asList(advertDbo, TestUtils.createAdvert(userDbo), TestUtils.createAdvert(userDbo), TestUtils.createAdvert(userDbo));
        advertDtos = advertDbos.stream().map(advertConverter::transform).collect(Collectors.toList());

//getCurrentUser
        Mockito.when(userRepository.getById(any(UUID.class))).thenReturn(userDbo);
        Mockito.when(authenticationService.getCurrentUser()).thenReturn(userDbo);

//getAllAdvertsByPageable
        Mockito.when(advertRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(advertDbos));

//getAllAdvertsByOwner
        Mockito.when(advertRepository.findByOwner(any(), any(Pageable.class))).thenReturn(new PageImpl(advertDbos));

//getAllAdvertsByStatus
        Mockito.when(advertRepository.findByAdvertStatus(advertDbo.getAdvertStatus())).thenReturn(advertDbos);

//getlAdvertById
        Mockito.when(advertRepository.findById(advertDbo.getId())).thenReturn(Optional.of(advertDbo));
        Mockito.when(advertRepository.getById(advertDbo.getId())).thenReturn(advertDbo);
    }

    @Test
    public void getAllAdvertsTest() {
        Page<AdvertDto> allAdverts = advertService.getAllAdverts(pageRequest);
        assertNotNull(allAdverts);
        assertTrue(advertDtos.size() == allAdverts.getContent().size());
    }

    @Test
    public void getAllAdvertsByUserTest() {
        Page<AdvertDto> allAdvertsByUser = advertService.getAllAdvertsByUser(pageRequest);
        assertNotNull(allAdvertsByUser);
        assertTrue(advertDtos.size() == allAdvertsByUser.getContent().size());
    }

    @Test
    public void getAllAdvertsByStatus() {
        List<AdvertDto> allAdvertsByStatus = advertService.getAllAdvertsByStatus(advertDbo.getAdvertStatus());
        assertNotNull(allAdvertsByStatus);
        assertTrue(advertDtos.size() == allAdvertsByStatus.size());
    }

    @Test
    public void getAdvertById() {
        AdvertDto advertById = advertService.getAdvertById(advertDbo.getId());
        assertNotNull(advertById);
        assertTrue(advertDbo.getId().equals(advertById.getId()));
    }

}
