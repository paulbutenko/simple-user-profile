package com.paul.userprofile.client;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class UserProfileApi {
    private static final String USER_PROFILES_URI = "/user-profiles";

    private final RestTemplate restTemplate;

    public UserProfileApi(final String url) {
        String rootUri = "http://" + url;
        RestTemplateBuilder builder = new RestTemplateBuilder()
            .requestFactory(new HttpComponentsClientHttpRequestFactory())
            .rootUri(rootUri);

        this.restTemplate = builder.build();
    }

    public List<UserProfile> getUserProfiles() {
        ResponseEntity<List<UserProfile>> response =
                restTemplate.exchange(USER_PROFILES_URI, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserProfile>>() {});
        return response.getBody();
    }

    public UserProfile getUserProfile(final Long id) {
        ResponseEntity<UserProfile> response = restTemplate.exchange(USER_PROFILES_URI + "/" + id, HttpMethod.GET, null, UserProfile.class);
        return response.getBody();
    }

    public UserProfile saveUserProfile(final UserProfile userProfile) {
        ResponseEntity<UserProfile> response = restTemplate.postForEntity(USER_PROFILES_URI, userProfile, UserProfile.class);
        return response.getBody();
    }

    public void deleteUserProfile(final Long id) {
        restTemplate.delete(USER_PROFILES_URI + "/" + id);
    }
}
