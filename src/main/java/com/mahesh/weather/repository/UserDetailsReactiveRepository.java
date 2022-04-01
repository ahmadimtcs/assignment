package com.mahesh.weather.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsReactiveRepository extends
    ReactiveMongoRepository<UserDetails, String> {

//    Mono<UserDetails> findByUserName(String userName);
}
