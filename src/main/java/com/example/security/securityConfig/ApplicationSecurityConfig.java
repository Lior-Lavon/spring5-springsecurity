package com.example.security.securityConfig;

import com.example.security.auth.ApplicationUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.security.securityConfig.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // enable controller permission access, enable @PreAuthorize
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                     ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder; // BCryptPasswordEncoder
        this.applicationUserService = applicationUserService;
    }

    // In Memory User Details Manager
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails tom = User.builder()
//                .username("tom")
//                .password(passwordEncoder.encode("tom"))
////                .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//
//        UserDetails linda = User.builder()
//                .username("linda")
//                .password(passwordEncoder.encode("linda"))
////                .roles(ADMIN.name()) // ROLE_ADMIN
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails anna = User.builder()
//                .username("anna")
//                .password(passwordEncoder.encode("anna"))
////                .roles(STUDENT.name()) // ROLE_STUDENT
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                tom, linda, anna
//        );
//    }


//    // wire daoAuthenticationProvider Bean to be used to load content from DB
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // this Bean provider replace 'protected UserDetailsService userDetailsService()' above
    // and set our own custom implementation of 'UserDetailsService' with my ApplicationUserService
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);

        return provider;
    }


////////////////////////////////////////////////////////////////////////////////////////////////


//    // Basic Authentication
//    ///////////////////////
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        //  Configure basic authentication, has no logout
//        //  Authorize requests / any request must be authenticated with user/pass and mechanisem is basic authentication
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
//                .antMatchers("/h2-console/**").permitAll() // h2
//                .antMatchers("/api/**").hasRole(STUDENT.name())
//
//// replaced by @EnableGlobalMethodSecurity(prePostEnabled = true) !!! Using annotations in Controller
////                .antMatchers(DELETE,"/management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
////                .antMatchers(PUT,"/management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
////                .antMatchers(POST,"/management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
////                .antMatchers(GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
//
//
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//
//        // csrf : Generate X-XSRF-TOKEN : Cross Site Request Forgery
////        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // Enable for Web Browsers
//        http.csrf().disable(); // disable for REST
////        http.headers().frameOptions().disable();
//    }

    // Form Based Authentication
    ////////////////////////////
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //  Configure Form Based authentication
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/courses", true)
                    .permitAll()
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                    .key("should_be_secured_value") // default to 2 weeks , changed to 21 days
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");

        http.headers().frameOptions().disable(); // H2 consul
    }
}
