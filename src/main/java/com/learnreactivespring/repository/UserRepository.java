package com.learnreactivespring.repository;


import com.learnreactivespring.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
