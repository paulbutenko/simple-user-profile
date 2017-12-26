package com.paul.userprofile.api;

import java.util.Date;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserProfileApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UserProfileApplication.class, args);
    }
    
    @Bean
    ApplicationRunner init(final UserProfileRepository repository) {
        return args -> {
            UserProfile userProfile = new UserProfile();
            userProfile.setFirstName("John");
            userProfile.setLastName("Dough");
            userProfile.setBirthDate(new Date());
            repository.save(userProfile);


            userProfile = new UserProfile();
            userProfile.setFirstName("Mona");
            userProfile.setLastName("Lisa");
            userProfile.setBirthDate(new Date());
            repository.save(userProfile);
        };
    }
}
