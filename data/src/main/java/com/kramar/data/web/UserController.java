package com.kramar.data.web;

import com.kramar.data.service.AuthenticationService;
import com.kramar.data.dto.ChangePasswordDto;
import com.kramar.data.dto.UserDto;
import com.kramar.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(UserController.REQUEST_MAPPING)
public class UserController {

    public static final String REQUEST_MAPPING = "/users";

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;


    /** Get current user info
     * @return user in current session
     */
    @GetMapping(value = "/info")
    public UserDto getCurrentUser() {
        UUID userId = authenticationService.getCurrentUserId();
        return userService.getUserById(userId);
    }

    /**
     * Get all users with pagination
     * @param pageable class with pagination information
     * @return advert with pagination
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAllUsers(final Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    /** Get user by id
     * @param id user id
     * @return user
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable("id") final UUID id) {
        return userService.getUserById(id);
    }

    /** Create new user
     * @param userDto new user
     * @return saved user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Validated final UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userService.createUser(userDto);
    }

    /** Modify user by id
     * @param id user id
     * @param userDto user
     * @return modified user
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto modifyUser(@PathVariable("id") final UUID id, @RequestBody @Validated final UserDto userDto) {
        return userService.modifyUserById(id, userDto);
    }

    /** Delete user by id
     * @param id user id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") final UUID id) {
        userService.deleteUserById(id);
    }

    /** Change user password
     * @param id user id
     * @param passwordDto Old and new password
     */
    @PutMapping("/changepassword/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable("id") final UUID id, @RequestBody @Validated final ChangePasswordDto passwordDto) {
        passwordDto.setOldPassword(passwordEncoder.encode(passwordDto.getOldPassword()));
        passwordDto.setNewPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userService.changePassword(id, passwordDto);
    }
}
