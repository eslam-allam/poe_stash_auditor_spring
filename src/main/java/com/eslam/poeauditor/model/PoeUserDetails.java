package com.eslam.poeauditor.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eslam.poeauditor.constant.UserRoleCode;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Samson Effes
 */
@Data
public class PoeUserDetails implements UserDetails {

    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;
    private Boolean enabled;
    private Boolean expired;
    private Boolean locked;
    

    public PoeUserDetails(User user) {
        userName = user.getEmailId();
        password = user.getPassword();
        authorities = user.getUserRoles().stream()
                .map(UserRole::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        enabled = user.getEnabled();
        expired = user.getExpired();
        locked = user.getLocked();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
