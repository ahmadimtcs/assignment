package com.weather.assignment.service;

import com.weather.assignment.document.WeatherElements;
import com.weather.assignment.repository.WeatherRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Date;
import java.util.Optional;
import static com.weather.assignment.constants.WeatherConstants.OPEN_WEATHER_URL;

@Service
public class WeatherService {

    @Autowired
    WeatherRepository weatherRepository;

    WebClient webClient = WebClient.create(OPEN_WEATHER_URL);

    @Value("${api.key}")
    private String apiKey;

    @Value(("${city.name}"))
    private String cityName;

    public void getWeather(){
        webClient.get().uri("/weather?q="+cityName+"&appid=" +apiKey).retrieve().bodyToMono(String.class).subscribe(s -> {
            JSONObject jsonObject = new JSONObject(s);
            WeatherElements elements=new WeatherElements();
            elements.setId(jsonObject.getLong("id"));
            elements.setName(jsonObject.getString("name"));
            if(Optional.ofNullable(jsonObject.getJSONObject("main")).isPresent()){
                JSONObject mainObject = jsonObject.getJSONObject("main");
                elements.setTemp(mainObject.getDouble("temp"));
                elements.setTemp_max(mainObject.getDouble("temp_max"));
                elements.setTemp_min(mainObject.getDouble("temp_min"));
                elements.setDate(new Date());
            }
            weatherRepository.save(elements).subscribe();
        });
    }
}
