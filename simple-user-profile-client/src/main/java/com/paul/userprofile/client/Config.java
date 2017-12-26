package com.paul.userprofile.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    
    @Value("${profile.api.url:localhost:8080}")
    private String url;

    @Bean
    public UserProfileApi patientDataRestRepository() {
        return new UserProfileApi(url);
    }
    
}
