package com.mahesh.weather.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    log.error("Exception is {}", ex.getMessage(), ex);
    var dataBufferFactory = exchange.getResponse().bufferFactory();
    var errorMessage = dataBufferFactory.wrap(ex.getMessage().getBytes());

    HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    if (ex instanceof BadDataException) {
      //If exception is BadDataException then return Bad Request as statusCode
      statusCode = HttpStatus.BAD_REQUEST;
    } else if (ex instanceof ResourceNotFoundException) {
      //If exception is ResourceNotFound then return Not Found as statusCode
      statusCode = HttpStatus.NOT_FOUND;
    }
    exchange.getResponse().setStatusCode(statusCode);
    return exchange.getResponse().writeWith(Mono.just(errorMessage));
  }
}
