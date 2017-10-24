package com.kramar.data.service.impl;

import com.kramar.data.converter.UserConverter;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.ChangePasswordDto;
import com.kramar.data.dto.UserDto;
import com.kramar.data.exception.ConflictException;
import com.kramar.data.exception.ErrorReason;
import com.kramar.data.service.AuthenticationService;
import com.kramar.data.service.UserService;
import com.kramar.data.repository.UserRepository;
import com.kramar.data.type.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Get all users with pagination
     * @param pageable class with pagination information
     * @return advert with pagination
     */
    @Override
    public Page<UserDto> getAllUsers(final Pageable pageable) {
        return userRepository.findAll(pageable).map(userConverter::transform);
    }

    /** Create new user
     * @param userDto new user
     * @return saved user
     */
    @Override
    public UserDto createUser(final UserDto userDto) {
        userDto.setId(null);
        UserDbo userDbo = userConverter.transform(userDto);
        userDbo = userRepository.save(userDbo);
        return userConverter.transform(userDbo);
    }

    /** Get user by id
     * @param id user id
     * @return user
     */
    @Override
    public UserDto getUserById(final UUID id) {
        final UserDbo userDbo = userRepository.getById(id);
        return userConverter.transform(userDbo);
    }

    /** Delete user by id
     * @param id user id
     */
    @Override
    public void deleteUserById(final UUID id) {
        final UserDbo userDbo = userRepository.getById(id);
        userRepository.delete(userDbo);
    }

    /** Modify user by id
     * @param id user id
     * @param userDto user
     * @return modified user
     */
    @Override
    public UserDto modifyUserById(final UUID id, final UserDto userDto) {
        final UserDbo oldUser = userRepository.getById(id);
        userDto.setId(oldUser.getId());
        // only super admin can change user roles
        if (userDto.getUserRoles().contains(UserRole.SUPER_ADMIN)
                && !getCurrentUser().getUserRoles().contains(UserRole.SUPER_ADMIN)) {
//            userDto.setUserRoles(oldUser.getUserRoles());
                throw new ConflictException(ErrorReason.INVALID_PERMISSION, "modifyUser");
        }
        UserDbo userDbo = userConverter.transform(userDto);
        userDbo = userRepository.save(userDbo);
        return userConverter.transform(userDbo);
    }

    private UserDto getCurrentUser() {
        UUID userId = authenticationService.getCurrentUserId();
        return getUserById(userId);
    }

    /** Change user password
     * @param id user id
     * @param passwordDto Old and new password
     */
    @Override
    public void changePassword(final UUID id, final ChangePasswordDto passwordDto) {
        final UserDbo userDbo = userRepository.getById(id);
        if (userDbo.getPassword().equals(passwordDto.getOldPassword())) {
            userDbo.setPassword(passwordDto.getNewPassword());
            userRepository.save(userDbo);
        } else {
            throw new ConflictException(ErrorReason.INVALID_PASSWORD, "changePassword");
        }
    }
}
