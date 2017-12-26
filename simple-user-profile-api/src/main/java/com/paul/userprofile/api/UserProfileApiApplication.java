package com.paul.userprofile.api;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserProfileApiApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UserProfileApiApplication.class, args);
    }
    
    @Bean
    ApplicationRunner init(final UserProfileRepository repository) {
        return args -> {
            UserProfile userProfile = new UserProfile();
            userProfile.setFirstName("John");
            userProfile.setLastName("Dough");
            userProfile.setBirthDate(LocalDate.of(1980, Month.DECEMBER, 26));
            repository.save(userProfile);


            userProfile = new UserProfile();
            userProfile.setFirstName("Mona");
            userProfile.setLastName("Lisa");
            userProfile.setBirthDate(LocalDate.of(1479, Month.JUNE, 15));
            repository.save(userProfile);
        };
    }
}
