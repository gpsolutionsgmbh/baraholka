package com.kramar.data.test.service;

import com.kramar.data.converter.UserConverter;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.ChangePasswordDto;
import com.kramar.data.dto.UserDto;
import com.kramar.data.exception.ConflictException;
import com.kramar.data.repository.UserRepository;
import com.kramar.data.service.UserService;
import com.kramar.data.type.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.kramar.data.test.utils.TestUtils.createUser;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserConverter userConverter;
    @MockBean
    private UserRepository userRepository;

    private UserDbo userDbo;
    private UserDto userDto;
    private List<UserDbo> userDbos;
    private List<UserDto> userDtos;
    private Pageable pageRequest;

    @Before
    public void setUp() {
        final UserConverter userConverterTemp = new UserConverter();
        final Sort sort = new Sort(Sort.Direction.ASC, "name");
        pageRequest = new PageRequest(1, 1, sort);
        userDbo = createUser();
        userDto = userConverterTemp.transform(userDbo);
        userDbos = Arrays.asList(userDbo, createUser(), createUser(), createUser());
        userDtos = userDbos.stream().map(userConverterTemp::transform).collect(Collectors.toList());

        when(userRepository.getById(any(UUID.class))).thenReturn(userDbo);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(userDbos));
        when(userRepository.save(any(UserDbo.class))).thenReturn(userDbo);
        doNothing().when(userRepository).delete(any(UUID.class));
        when(userConverter.transform(any(UserDbo.class))).thenReturn(userDto);
        when(userConverter.transform(any(UserDto.class))).thenReturn(userDbo);
    }

    @Test
    public void getAllUsersTest() {
        Page<UserDto> allUsers = userService.getAllUsers(pageRequest);
        assertNotNull(allUsers);
        assertTrue(userDbos.size() == allUsers.getContent().size());
    }

    @Test
    public void getUserByIdTest() {
        UserDto userDto = userService.getUserById(userDbo.getId());
        assertNotNull(userDto);
        assertTrue(userDto.getId().equals(userDbo.getId()));
    }

    @Test
    public void createUserTest() {
        UserDto newUser = userService.createUser(userDto);
        assertNotNull(newUser);
        assertTrue(newUser.getUserName().equals(userDto.getUserName()));
    }

    @Test
    public void modifyUserByIdTest() throws Exception {
        userDto.setUserName("User");
        userDto.setUserRoles(Collections.singletonList(UserRole.SUPER_ADMIN));
        UserDto modifyUserById = userService.modifyUserById(userDto.getId(), userDto);
        assertNotNull(modifyUserById);
        assertTrue(modifyUserById.getUserName().equals(userDto.getUserName()));
    }

    @Test
    public void deleteUserByIdTest() {
        userService.deleteUserById(userDto.getId());
    }

    @Test
    public void changeUserPasswordTest() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword(userDbo.getPassword());
        changePasswordDto.setNewPassword("P@ssw0rd");
        userService.changePassword(userDto.getId(), changePasswordDto);
    }

    @Test(expected = ConflictException.class)
    public void changeIncorrectUserPasswordTest() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("P@ssw0rd");
        changePasswordDto.setNewPassword("P@ssw0rd");
        userService.changePassword(userDto.getId(), changePasswordDto);
    }

}
