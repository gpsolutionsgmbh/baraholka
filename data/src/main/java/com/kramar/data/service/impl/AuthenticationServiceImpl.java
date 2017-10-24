package com.kramar.data.service.impl;

import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.CustomGrantedAuthorities;
import com.kramar.data.dto.CustomUserDetails;
import com.kramar.data.service.AuthenticationService;
import com.kramar.data.repository.UserRepository;
import com.kramar.data.type.UserRole;
import com.kramar.data.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UUID getCurrentUserId() {
        final CustomUserDetails userDetails = getUserDetails();
        return (userDetails == null) ? null : userDetails.getUserId();
    }
    @Override
    public UserDbo getCurrentUser() {
        final CustomUserDetails userDetails = getUserDetails();
        return (userDetails == null) ? null : userRepository.getById(userDetails.getUserId());
    }

    private CustomUserDetails getUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof OAuth2Authentication) {
            if (((OAuth2Authentication) authentication).getUserAuthentication() instanceof UsernamePasswordAuthenticationToken) {
                final UsernamePasswordAuthenticationToken userAuthentication = (UsernamePasswordAuthenticationToken) ((OAuth2Authentication) authentication).getUserAuthentication();
                if (userAuthentication.getPrincipal() instanceof CustomUserDetails) {
                    return (CustomUserDetails) userAuthentication.getPrincipal();
                }

                final LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) userAuthentication.getDetails();
                final LinkedHashMap<String, Object> principal = (LinkedHashMap<String, Object>) details.get("principal");

                return transform(principal);
            }
        }
        return null;
    }

    public CustomUserDetails transform(final LinkedHashMap<String, Object> map) {
        final CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId(UUID.fromString((String) map.get("userId")));
        userDetails.setEmail((String) map.get("email"));
        userDetails.setUserStatus(UserStatus.valueOf((String) map.get("userStatus")));

        final List<Map<String, String>> authorities = (List<Map<String, String>>) map.get("authorities");
        final List<CustomGrantedAuthorities> grantedAuthorities = authorities.stream()
                .map(s -> new CustomGrantedAuthorities(UserRole.valueOf(s.get("authority"))))
                .collect(Collectors.toList());
        userDetails.setAuthorities(grantedAuthorities);
        return userDetails;
    }

}
