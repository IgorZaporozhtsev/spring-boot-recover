package com.zeecoder.reboot.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@NoArgsConstructor
@Getter
@Setter
public class JwtConfig {

    private String key;
    private String prefix;
    private Integer expiration;


    public String getAuthHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
