package com.example.cloudservice.mapper;

import com.example.cloudservice.entity.Role;
import com.example.cloudservice.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    public UserDetails mapUserToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).toList()
        );
    }

}
