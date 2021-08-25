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

  @Value("${weather.api.lon-left}")
  private String lonleft;

  @Value("${weather.api.lon-right}")
  private String lonright;

  @Value("${weather.api.lat-bottom}")
  private String latbottom;

  @Value("${weather.api.lat-top}")
  private String lattop;

  @Value("${weather.api.zoom}")
  private String zoom;


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

    http://api.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=663bb81b407b9d418b985005577dc473
    webClient.get().
        uri("/city?bbox="+lonleft+","+lonright+","+latbottom+","+lattop+","+zoom+"&appid=" + apiId).
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
          }

          cityRepository.save(cityDetails).subscribe();
        });
      }
    });
    start = Instant.now();
  }

}

