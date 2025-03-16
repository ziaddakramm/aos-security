package com.sumerge.security.repository;

import java.util.Optional;

import com.sumerge.security.model.ApplicationUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, String> {

  Optional<ApplicationUser> findByEmail(String email);

}
