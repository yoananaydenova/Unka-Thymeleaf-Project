package com.yoanan.unka.config;

import com.yoanan.unka.service.impl.UnkaUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity //Can be omitted because of WebSecurityConfigurerAdapter
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UnkaUserDetailsService unkaUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UnkaUserDetailsService unkaUserDetailsService, PasswordEncoder passwordEncoder) {
        this.unkaUserDetailsService = unkaUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // Allow access to static resources to anyone
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
                // Allow access to index, user login and registration to anyone
                .antMatchers("/", "/about",
                        "/users/login", "/users/register",
                        "/become-teacher", "/courses/all","/courses/all/**", "/courses/course/**").permitAll()
                // Protect all other pages
                .antMatchers("/courses/add", "/lessons/add","/exercises/add**", "/board").hasRole("TEACHER")
                .antMatchers("/**").authenticated()
                // Configure login with HTML form
                .and()
                  // Our login page will be served by the controller with mapping /users/login
                  .formLogin()
                  .loginPage("/users/login")
                  // The name of the user name input field of OUR login form is username (optional)
                  .usernameParameter("username")
                  // The name of the user password input field of OUR login form is password (optional)
                  .passwordParameter("password")
                  // On login success redirect here
                  .defaultSuccessUrl("/home")
                  // On login failure redirect here
                  .failureForwardUrl("/users/login-error")
                .and()
                  .logout()
                  // Which endpoin performs logout, e.g. http://localhost:8080/logout (! This shoud be POST requst!)
                  .logoutUrl("/logout")
                  // Where to land after logout
                  .logoutSuccessUrl("/")
                   // Remove the session from the server
                  .invalidateHttpSession(true)
                  // Delete the session cookie
                  .deleteCookies("JSESSIONID"); // Bye! :-)

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(unkaUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
