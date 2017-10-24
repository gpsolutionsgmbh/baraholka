package com.kramar.security.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(UserManagementController.REQUEST_MAPPING)
public class UserManagementController {
    public static final String REQUEST_MAPPING = "/oauth/user";

    @GetMapping
    public Principal userInfo(@AuthenticationPrincipal final Principal user) {
        return user;
    }
}
