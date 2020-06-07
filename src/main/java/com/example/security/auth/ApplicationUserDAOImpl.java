package com.example.security.auth;

import com.example.security.securityConfig.ApplicationUserRole;
import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake")
public class ApplicationUserDAOImpl implements ApplicationUserDAO {

    private final PasswordEncoder passwordEncoder;

    public ApplicationUserDAOImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser("anna",
                        passwordEncoder.encode("anna"),
                        ApplicationUserRole.STUDENT.getGrantedAuthorities(),
                        true, true, true, true),
                new ApplicationUser("linda",
                        passwordEncoder.encode("linda"),
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        true, true, true, true),
                new ApplicationUser("tom",
                        passwordEncoder.encode("tom"),
                        ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
                        true, true, true, true)
        );

        return applicationUsers;
    }
}
