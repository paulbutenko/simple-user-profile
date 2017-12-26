package com.paul.userprofile.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
