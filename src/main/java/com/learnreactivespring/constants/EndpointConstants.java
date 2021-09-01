package com.learnreactivespring.constants;

public class EndpointConstants {

  public static final String CITY_END_POINT = "/city";
  public static final String CITIES_END_POINT = "/cities";

  public static final String WEATHER_CITY_END_POINT = "/weather/{cityName}";
  public static final String STREAM_WEATHER_END_POINT = "/weather/stream";


  public static final String USER_SIGNIN_END_POINT = "/auth/signin";
  public static final String USER_SIGNUP_END_POINT = "/auth/signup";


  public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
  public static final String SIGNING_KEY = "devglan123r";
  public static final String AUTHORITIES_KEY = "scopes";
}
