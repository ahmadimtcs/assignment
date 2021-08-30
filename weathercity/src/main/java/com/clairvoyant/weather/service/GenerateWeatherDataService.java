package com.clairvoyant.weather.service;

import static com.clairvoyant.weather.contants.CityConstants.OPEN_WEATHER_COMMON_URL;

import com.clairvoyant.weather.model.City;
import com.clairvoyant.weather.repository.CityRepository;
import java.time.Duration;
import java.time.Instant;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
public class GenerateWeatherDataService {

  @Autowired
  private CityRepository cityRepository;

  @Value("${weather.api.id}")
  private String apiId;

  @Value("${weather.api.lat}")
  private String lat;

  @Value("${weather.api.lon}")
  private String lon;


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

    http://api.openweathermap.org/data/2.5/find?lat=55.5&lon=37.5&cnt=10&appid=8f6c24bde958b554c27f97379efd6a5c
    webClient.get().
        uri("?lat="+lat+"&lon="+lon+"&appid=" + apiId).
        retrieve().bodyToMono(String.class).subscribe(v -> {
      JSONObject jsonObject = new JSONObject(v);

      if(jsonObject.getInt("cod")==200)
      {
        JSONArray arr = jsonObject.getJSONArray("list");
        arr.forEach(item -> {
          City cityDetails = new City();
          JSONObject obj = (JSONObject) item;
          cityDetails.setId(obj.getLong("id"));
          cityDetails.setName(obj.getString("name"));
          if (obj.getJSONObject("main") != null) {
            JSONObject mainObject = obj.getJSONObject("main");
            cityDetails.setTemp(mainObject.getDouble("temp"));
            cityDetails.setFeels_like(mainObject.getDouble("feels_like"));
            cityDetails.setTemp_max(mainObject.getDouble("temp_max"));
            cityDetails.setTemp_min(mainObject.getDouble("temp_min"));
            cityDetails.setPressure(mainObject.getDouble("pressure"));
          }

          cityRepository.save(cityDetails).subscribe();
        });
      }
    });
    start = Instant.now();
  }

}

