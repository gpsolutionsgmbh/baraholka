package com.kramar.data.test.repository;

import com.kramar.data.dbo.UserDbo;
import com.kramar.data.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static com.kramar.data.test.utils.TestUtils.INVALID_ID;
import static com.kramar.data.test.utils.TestUtils.createUser;
import static junit.framework.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserDbo userDbo;

    @Before
    public void setUp() {
        userDbo = createUser();
        userDbo = userRepository.save(userDbo);
    }

    @After
    public void del() {
        userRepository.delete(userDbo.getId());
    }

    @Test
    public void findByIdTest() {
        Optional<UserDbo> byId = userRepository.findById(userDbo.getId());
        assertTrue(byId.isPresent());
        assertTrue(byId.get().getId().equals(userDbo.getId()));

        byId = userRepository.findById(INVALID_ID);
        assertFalse(byId.isPresent());
    }

    @Test
    public void getByIdTest() {
        UserDbo byId = userRepository.getById(userDbo.getId());
        assertNotNull(byId);
        assertTrue(byId.getId().equals(userDbo.getId()));
    }

    @Test
    public void findByEmailTest() {
        Optional<UserDbo> byEmail = userRepository.findByEmail(userDbo.getEmail());
        assertTrue(byEmail.isPresent());
        assertTrue(byEmail.get().getEmail().equals(userDbo.getEmail()));
    }

    @Test
    public void getByEmailTest() {
        UserDbo byEmail = userRepository.getUserByEmail(userDbo.getEmail());
        assertNotNull(byEmail);
        assertTrue(byEmail.getEmail().equals(userDbo.getEmail()));
    }

}
