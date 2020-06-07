package com.example.security.auth;

import com.example.security.auth.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserDAO {

    Optional<ApplicationUser> selectApplicationUserByUserName(String username);
}
