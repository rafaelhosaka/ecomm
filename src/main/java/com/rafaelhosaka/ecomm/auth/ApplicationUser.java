package com.rafaelhosaka.ecomm.auth;

import com.rafaelhosaka.ecomm.account.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationUser implements UserDetails {

    private UserAccount userAccount;

    public ApplicationUser(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> roles = userAccount.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toSet());
        return roles;
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        if(userAccount == null){
            return false;
        }
        return userAccount.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        if(userAccount == null){
            return false;
        }
        return userAccount.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if(userAccount == null){
            return false;
        }
        return userAccount.isActive();
    }

    @Override
    public boolean isEnabled() {
        if(userAccount == null){
            return false;
        }
        return userAccount.isActive();
    }

}
