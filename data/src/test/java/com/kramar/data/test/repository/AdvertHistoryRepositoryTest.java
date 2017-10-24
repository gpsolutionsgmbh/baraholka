package com.kramar.data.test.repository;

import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.AdvertHistoryDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.repository.AdvertHistoryRepository;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.repository.UserRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.kramar.data.test.utils.TestUtils.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdvertHistoryRepositoryTest {

    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private AdvertHistoryRepository advertHistoryRepository;
    @Autowired
    private UserRepository userRepository;

    private AdvertHistoryDbo advertHistoryDbo;
    private UserDbo userDbo;
    AdvertDbo advertDbo;

    @Before
    public void setUp() {
        userDbo = createUser();
        userDbo = userRepository.save(userDbo);
        advertDbo = createAdvert(userDbo);
        advertDbo = advertRepository.save(advertDbo);
        advertHistoryDbo = createAdvertHistory(advertDbo);
        advertHistoryDbo = advertHistoryRepository.save(advertHistoryDbo);
    }

    @After
    public void del() {
        advertRepository.delete(advertDbo);
        userRepository.delete(userDbo.getId());
        advertHistoryRepository.delete(advertHistoryDbo.getId());
    }

    @Test
    public void findByOwnerTest() {
        List<AdvertHistoryDbo> advertHistoryDbos = advertHistoryRepository.findByOwner(userDbo.getId());

        assertTrue(CollectionUtils.isNotEmpty(advertHistoryDbos));
        assertTrue(advertHistoryDbos.get(0).getHeadLine().equals(ANY_WORD));

        advertHistoryDbos = advertHistoryRepository.findByOwner(INVALID_ID);
        assertFalse(CollectionUtils.isNotEmpty(advertHistoryDbos));
    }

}
