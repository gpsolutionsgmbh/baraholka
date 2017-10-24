package com.kramar.data.dto;

import com.kramar.data.type.UserStatus;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = -5589275374200098211L;

    private UUID userId;
    private String email;
    @JsonIgnore
    private String password;
    private UserStatus userStatus;
    private List<CustomGrantedAuthorities> authorities = new ArrayList<>(0);

    @Override
    public List<CustomGrantedAuthorities> getAuthorities() {
        return authorities;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void setAuthorities(List<CustomGrantedAuthorities> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !UserStatus.BLOCKED.equals(userStatus);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(userStatus);
    }
}
