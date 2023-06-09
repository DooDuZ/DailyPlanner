package com.planner.config;

import com.planner.filter.LoginFailureHandler;
import com.planner.filter.LoginSuccessHandler;
import com.planner.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@EnableWebSecurity
@Configuration
public class WebSecurityConfigure {

    LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler();
    LoginFailureHandler loginFailureHandler = new LoginFailureHandler();
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, UserService userService) throws Exception {
        httpSecurity
            .authorizeHttpRequests()
                .antMatchers("/").hasRole("일반회원")
                .antMatchers("/**").permitAll()
            .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .usernameParameter("uid")
                .passwordParameter("upassword")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            .and()
                .csrf()
                .disable();

        return httpSecurity.build();
    }
}
