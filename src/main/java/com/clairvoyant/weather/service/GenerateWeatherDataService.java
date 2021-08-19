package com.clairvoyant.weather.service;

import static com.clairvoyant.weather.contants.WeatherConstants.OPEN_WEATHER_COMMON_URL;

import com.clairvoyant.weather.document.WeatherDetails;
import com.clairvoyant.weather.repository.WeatherRepository;
import java.time.Duration;
import java.time.Instant;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 19-08-2021 15:27
 */
@Component
public class GenerateWeatherDataService {

  @Autowired
  WeatherRepository weatherRepository;

  @Value("${weather.api.id}")
  private String apiId;

  WebClient webClient = WebClient.create(OPEN_WEATHER_COMMON_URL);
  Instant start = Instant.now();

  public void refreshAfterTime() {

    Instant end = Instant.now();
    Duration timeElapsed = Duration.between(start, end);
    if (timeElapsed.toMinutes() >= 1) {
      refreshData();
      start = Instant.now();
    }

  }

  public void refreshData() {

    webClient.get().
        uri("/find?lat=19.076090&lon=72.877426&cnt=10&appid=" + apiId).
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
