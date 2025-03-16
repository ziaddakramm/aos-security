package com.aos.repository;

import java.util.Optional;

import com.aos.model.ApplicationUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, String> {

  Optional<ApplicationUser> findByEmail(String email);

}
