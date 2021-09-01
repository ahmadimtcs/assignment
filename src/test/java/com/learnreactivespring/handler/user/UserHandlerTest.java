package com.learnreactivespring.handler.user;

import com.learnreactivespring.model.User;
import com.learnreactivespring.repository.UserRepository;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
class UserHandlerTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  WebTestClient webTestClient;

  String AuthToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWtlc2FqMyIsImlhdCI6MTYzMDM1MTEyMiwiZXhwIjoxNjMwMzY5MTIyfQ.enUaG8sc7NgTsRgd9XdjaS8udXVjUXcsItRbIttCD0k";

  @BeforeEach
  void addCityData() {

    User user1 = new User("john", "123");
    userRepository.save(user1).block();
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();

  }


  @Test
  void updateUser() {

    HashMap<String, String> map = new HashMap<>();
    map.put("patel", "133442");

    Flux<String> responseFlux = webTestClient
        .put()
        .uri("/updateUser/john")
        .body(Mono.just(map), User.class)
        .header("Authorization", AuthToken)
        .exchange()
        .expectStatus()
        .isOk().returnResult(String.class).getResponseBody().log();

    StepVerifier.create(responseFlux)
        .expectSubscription()
        .expectNext("user updated")
        .verifyComplete();
  }

  @Test
  void getUserById() {
    webTestClient.get().uri("/getUser/john")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectBody(User.class);
  }

  @Test
  void getUserByIdNotFound() {
    webTestClient.get().uri("/getUser/james")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  void getAllUsers() {
    webTestClient.get().uri("/getUsers")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  void deleteUser() {

    webTestClient.delete().uri("/deleteUser/john")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  void deleteUserNotFound() {
    webTestClient.delete().uri("/deleteUser/matt")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isNotFound();
  }


}