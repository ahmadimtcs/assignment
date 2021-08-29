package com.weather.assignment;

import com.weather.assignment.document.WeatherDetails;
import com.weather.assignment.repository.WeatherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;
import java.util.Objects;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WeatherHandlerTest {

  @Autowired private WeatherRepository weatherRepository;

  @Test
  public void getWeatherDetails() {
    StepVerifier.create(weatherRepository.findAll())
        .expectSubscription()
        .expectNextCount(1)
        .verifyComplete();
  }

  @Test
  public void createWeatherDetails() {
    WeatherDetails weatherDetails = new WeatherDetails();
    weatherDetails.setId(2949024L);
    weatherDetails.setName("london");
    weatherDetails.setTemp(249.99);
    weatherDetails.setTempMax(250.99);
    weatherDetails.setTempMin(245.99);
    weatherDetails.setDate(new Date());
    Mono<WeatherDetails> savedDetail = weatherRepository.save(weatherDetails);
    StepVerifier.create(savedDetail)
        .expectSubscription()
        .expectNextMatches(detail -> detail.getId() != null && detail.getName().equals("london"))
        .verifyComplete();
  }

  @Test
  public void updateWeatherDetails() {
    Mono<WeatherDetails> updatedWeather =
        weatherRepository
            .findById(2949024L)
            .map(
                details -> {
                  details.setName("Mumbai");
                  return details;
                })
            .flatMap((details) -> weatherRepository.save(details));
    StepVerifier.create(updatedWeather)
        .expectSubscription()
        .expectNextMatches(elements -> Objects.equals(elements.getName(), "Mumbai"))
        .verifyComplete();
  }

  @Test
  public void deleteWeatherDetailsById() {
    // map method to get id from mono of items objects
    // flatmap to perform actual delete operation
    Mono<Void> deletedWeather =
        weatherRepository
            .findById(2949024L)
            .map(WeatherDetails::getId)
            .flatMap((id) -> weatherRepository.deleteById(id));
    StepVerifier.create(deletedWeather.log()).expectSubscription().verifyComplete();
    StepVerifier.create(weatherRepository.findAll().log("New Details: "))
        .expectSubscription()
        .expectNextCount(0)
        .verifyComplete();
  }
}
