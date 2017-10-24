package com.kramar.data.test.repository;

import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static com.kramar.data.test.utils.TestUtils.*;
import static junit.framework.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdvertRepositoryTest {

    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private UserRepository userRepository;

    private AdvertDbo advertDbo;
    private UserDbo userDbo;

    @Before
    public void setUp() {
        userDbo = createUser();
        userDbo = userRepository.save(userDbo);
        advertDbo = createAdvert(userDbo);
        advertDbo = advertRepository.save(advertDbo);
    }

    @After
    public void del() {
        advertRepository.delete(advertDbo.getId());
        userRepository.delete(userDbo.getId());
    }

    @Test
    public void findByHeadLineTest() {
        Optional<AdvertDbo> byHeadLine = advertRepository.findByHeadLine(advertDbo.getHeadLine());
        assertTrue(byHeadLine.isPresent());
        assertTrue(byHeadLine.get().getHeadLine().equals(advertDbo.getHeadLine()));

        byHeadLine = advertRepository.findByHeadLine("abra_cadabra");
        assertFalse(byHeadLine.isPresent());
    }

    @Test
    public void findByIdTest() {
        Optional<AdvertDbo> byId = advertRepository.findById(advertDbo.getId());
        assertTrue(byId.isPresent());
        assertTrue(byId.get().getHeadLine().equals(advertDbo.getHeadLine()));

        byId = advertRepository.findById(INVALID_ID);
        assertFalse(byId.isPresent());
    }

    @Test
    public void getByIdTest() {
        AdvertDbo byId = advertRepository.getById(advertDbo.getId());
        assertNotNull(byId);
        assertTrue(byId.getHeadLine().equals(advertDbo.getHeadLine()));
    }

    @Test
    public void findByOwnerTest() {
        Page<AdvertDbo> byOwner = advertRepository.findByOwner(userDbo, null);
        assertFalse(byOwner.getContent().isEmpty());
        assertTrue(byOwner.getContent().get(0).getHeadLine().equals(advertDbo.getHeadLine()));
        UserDbo userDbo = new UserDbo();
        userDbo = userRepository.save(userDbo);
        byOwner = advertRepository.findByOwner(userDbo, null);
        assertTrue(byOwner.getContent().isEmpty());
    }

}
