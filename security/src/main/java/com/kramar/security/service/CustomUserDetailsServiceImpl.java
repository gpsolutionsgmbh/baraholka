package com.kramar.security.service;

import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.CustomGrantedAuthorities;
import com.kramar.data.dto.CustomUserDetails;
import com.kramar.data.repository.UserRepository;
import com.kramar.data.type.UserRole;
import com.kramar.data.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final UserDbo user = userRepository.getUserByEmail(email);
        final UserDetails userDetails = transform(user);
        return userDetails;
    }

    public CustomUserDetails transform(final UserDbo user) {
        if (user == null) return null;

        final CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId(user.getId());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        userDetails.setUserStatus(user.getStatus());
        userDetails.setAuthorities(transformAuthorities(user));

        return userDetails;
    }

    public CustomUserDetails transform(final LinkedHashMap<String, String> map) {
        final CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId(UUID.fromString(map.get("userId")));
        userDetails.setEmail(map.get("email"));
        userDetails.setUserStatus(UserStatus.valueOf(map.get("userStatus")));
        return userDetails;
    }

    private List<CustomGrantedAuthorities> transformAuthorities(final UserDbo user) {
        if (user == null) return Collections.EMPTY_LIST;

        final List<UserRole> userRoles = user.getUserRoles();

        if (userRoles.isEmpty()) return Collections.EMPTY_LIST;

        final List<CustomGrantedAuthorities> collect = userRoles.stream().map(CustomGrantedAuthorities::new).collect(Collectors.toList());

        return collect;
    }
}
