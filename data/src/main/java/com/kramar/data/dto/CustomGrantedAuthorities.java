package com.kramar.data.dto;

import com.kramar.data.type.UserRole;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthorities implements GrantedAuthority {

    @JsonIgnore
    private UserRole role;

    public CustomGrantedAuthorities(UserRole role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }

}
