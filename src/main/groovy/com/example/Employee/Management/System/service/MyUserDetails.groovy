package com.example.Employee.Management.System.service

import com.example.Employee.Management.System.model.Employee
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import java.util.stream.Collectors


class MyUserDetails implements UserDetails {

    private Employee employee
    private List<GrantedAuthority> authorities

    MyUserDetails(Employee employee) {
        this.employee = employee
        this.authorities = Arrays.stream(employee.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        authorities
    }

    @Override
    String getPassword() {
        employee.password
    }

    @Override
    String getUsername() {
        employee.username
    }

    @Override
    boolean isAccountNonExpired() {
        true
    }

    @Override
    boolean isAccountNonLocked() {
        true
    }

    @Override
    boolean isCredentialsNonExpired() {
        true
    }

    @Override
    boolean isEnabled() {
        employee.isEnabled
    }
}

