package com.kramar.data.service;

import com.kramar.data.dto.ChangePasswordDto;
import com.kramar.data.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    Page<UserDto> getAllUsers(final Pageable pageable);

    UserDto createUser(final UserDto userDto);

    UserDto getUserById(final UUID id);

    void deleteUserById(final UUID id);

    UserDto modifyUserById(final UUID id, final UserDto userDto);

    void changePassword(final UUID id, final ChangePasswordDto passwordDto);

}
