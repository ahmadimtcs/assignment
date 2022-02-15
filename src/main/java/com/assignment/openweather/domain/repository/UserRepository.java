package com.assignment.openweather.domain.repository;

import com.assignment.openweather.domain.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {

    Optional<Mono<UserEntity>> findByUsername(String userName);

    Boolean existsByUsername(String userName);
}
