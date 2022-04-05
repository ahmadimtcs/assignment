package com.mahesh.weather.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CommonUtil {

  public static Mono<String> getLoggedInUserId() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .map(authentication -> {
          log.info("Logged In UserName :=> {}", authentication.getName());
          return authentication.getName();
        })
        .switchIfEmpty(Mono.defer(() -> {
          log.info("Logged in username is not found. Returning user as username");
          return Mono.just("user");
        }));
  }

}
