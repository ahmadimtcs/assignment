package com.assignment.service;

import java.time.Duration;
import java.time.Instant;

import com.assignment.entity.City;
import com.assignment.repository.CityRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.assignment.constant.CityConstants.OPEN_WEATHER_COMMON_URL;


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
                                cityDetails.setHumidity(mainObject.getLong("humidity"));
                                cityDetails.setSea_level(mainObject.getLong("sea_level"));
                                cityDetails.setGrnd_level(mainObject.getLong("grnd_level"));

                            }
                            if(obj.getJSONObject("sys")!=null){
                                JSONObject mainObject = obj.getJSONObject("sys");
                                cityDetails.setCountry(mainObject.getString("country"));
                            }


                            cityRepository.save(cityDetails).subscribe();
                        });
                    }
                });
        start = Instant.now();
    }

}

