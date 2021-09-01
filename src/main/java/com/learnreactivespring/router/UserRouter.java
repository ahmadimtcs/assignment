package com.learnreactivespring.router;

import static com.learnreactivespring.constants.EndpointConstants.USER_SIGNIN_END_POINT;
import static com.learnreactivespring.constants.EndpointConstants.USER_SIGNUP_END_POINT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.learnreactivespring.handler.auth.AuthHandler;
import com.learnreactivespring.handler.user.UserHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {


  @RouterOperations({
      @RouterOperation(path = "/getUsers", beanClass = UserHandler.class, beanMethod = "getAllUsers"),
      @RouterOperation(path = "/getUser/{username}", beanClass = UserHandler.class, beanMethod = "getUserById"),
      @RouterOperation(path = "/updateUser", beanClass = UserHandler.class, beanMethod = "updateUser"),
      @RouterOperation(path = "/deleteUser/{username}", beanClass = UserHandler.class, beanMethod = "deleteUser")})
  @Bean
  public RouterFunction<ServerResponse> userRoute(UserHandler userHandler) {

    return RouterFunctions
        .route(GET("/getUsers").and(accept(APPLICATION_JSON)), userHandler::getAllUsers)
        .andRoute(GET("/getUser/{username}").and(accept(APPLICATION_JSON)),
            userHandler::getUserById)
        .andRoute(PUT("/updateUser/{username}").and(accept(APPLICATION_JSON)), userHandler::updateUser)
        .andRoute(DELETE("/deleteUser/{username}").and(accept(APPLICATION_JSON)),
            userHandler::deleteUser);
  }

  @RouterOperations({
      @RouterOperation(path = USER_SIGNIN_END_POINT, beanClass = AuthHandler.class, beanMethod = "signin"),
      @RouterOperation(path = USER_SIGNUP_END_POINT, beanClass = AuthHandler.class, beanMethod = "signup"),
  })

  @Bean
  public RouterFunction authRoute(AuthHandler authHandler) {
    return RouterFunctions
        .route(POST(USER_SIGNIN_END_POINT).and(accept(APPLICATION_JSON)), authHandler::signin)
        .andRoute(POST(USER_SIGNUP_END_POINT).and(accept(APPLICATION_JSON)), authHandler::signup);
  }


}
