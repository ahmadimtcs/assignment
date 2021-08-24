package com.clairvoyant.weather.service;

import static com.clairvoyant.weather.contants.WeatherConstants.OPEN_WEATHER_COMMON_URL;

import com.clairvoyant.weather.document.WeatherDetails;
import com.clairvoyant.weather.repository.WeatherRepository;
import java.time.Duration;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 19-08-2021 15:27
 */
@Service
@Slf4j
public class GenerateWeatherDataService {

  @Autowired
  WeatherRepository weatherRepository;

  @Value("${weather.api.id}")
  private String apiId;

  @Value("${weather.api.lon}")
  private String lon;

  @Value("${weather.api.lat}")
  private String lat;

  @Value("${weather.api.refresh.minutes}")
  private Double minutes;

  WebClient webClient = WebClient.create(OPEN_WEATHER_COMMON_URL);
  Instant start = Instant.now();

  public void refreshAfterTime() {

    Instant end = Instant.now();
    Duration timeElapsed = Duration.between(start, end);
    if (timeElapsed.toMinutes() >= minutes) {
      refreshData();
      start = Instant.now();
    }

  }

  public void refreshData() {
    log.info("refresh Data Invoked");
    webClient.get().
        uri("/find?lat=" + lat + "&lon=" + lon + "&cnt=10&appid=" + apiId).
        retrieve().bodyToMono(String.class).subscribe(v -> {
      JSONObject jsonObject = new JSONObject(v);
      if (jsonObject.getString("cod").equals("200")) {
        JSONArray arr = jsonObject.getJSONArray("list");
        arr.forEach(item -> {
          WeatherDetails weatherDetails = new WeatherDetails();
          JSONObject obj = (JSONObject) item;
          weatherDetails.setId(obj.getLong("id"));
          weatherDetails.setName(obj.getString("name"));
          if (obj.getJSONObject("main") != null) {
            JSONObject mainObject = obj.getJSONObject("main");
            weatherDetails.setTemp(mainObject.getDouble("temp"));
            weatherDetails.setFeels_like(mainObject.getDouble("feels_like"));
          }
          weatherRepository.save(weatherDetails).subscribe();
        });
      }
    });
    start = Instant.now();
  }


}
