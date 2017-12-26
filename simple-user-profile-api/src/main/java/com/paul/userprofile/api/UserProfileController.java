package com.paul.userprofile.api;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {
    
    private final UserProfileRepository repository;

    public UserProfileController(final UserProfileRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/user-profiles")
    public ResponseEntity<Collection<UserProfile>> userProfiles() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @PostMapping(path = "/user-profiles", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserProfile> save(@RequestBody final UserProfile request) {    	
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(request));
    }

    @GetMapping(path = "/user-profiles/{id}")
    public ResponseEntity<UserProfile> get(@PathVariable final Long id) throws IOException {
        UserProfile userProfile = repository.getOne(id);

        if (userProfile != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userProfile);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping(path = "/user-profiles/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {        
        repository.delete(id);        
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
