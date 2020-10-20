package com.zeecoder.reboot.security;

import com.zeecoder.reboot.security.jwt.JwtConfig;
import com.zeecoder.reboot.security.jwt.JwtTokenVerifier;
import com.zeecoder.reboot.security.jwt.JwtUsernameAndPasswordAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@ComponentScan("com.zeecoder.reboot.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final AuthSuccessHandlerImpl successHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                          AuthSuccessHandlerImpl successHandler,
                          AuthFailureHandlerImpl failureHandler,
                          JwtConfig jwtConfig,
                          SecretKey secretKey, AuthSuccessHandlerImpl successHandler1) {
        this.userDetailsService = userDetailsService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.successHandler = successHandler1;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilter(new JwtUsernameAndPasswordAuthFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthFilter.class)
            .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/account/**").permitAll()//.hasAnyAuthority("ADMIN")
                .antMatchers("/api/**").hasAnyAuthority("ADMIN")
                .antMatchers("/user_page/**").hasAnyAuthority("USER", "ADMIN")
            .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
