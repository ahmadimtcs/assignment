package com.clairvoyant.weather.initialize;

import com.clairvoyant.weather.service.GenerateWeatherDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 19-08-2021 15:48
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


  private final GenerateWeatherDataService generateWeatherDataService;

  public CommandLineRunnerImpl(
      GenerateWeatherDataService generateWeatherDataService) {
    this.generateWeatherDataService = generateWeatherDataService;
  }

  @Override
  public void run(String... args) throws Exception {

    generateWeatherDataService.refreshData();
  }

}
