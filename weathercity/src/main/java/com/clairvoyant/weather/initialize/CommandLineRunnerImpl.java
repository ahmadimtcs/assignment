package com.clairvoyant.weather.initialize;

import com.clairvoyant.weather.service.GenerateWeatherDataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

  @Autowired
  private GenerateWeatherDataService generateWeatherDataService;

  public CommandLineRunnerImpl(GenerateWeatherDataService generateWeatherDataService) {
    this.generateWeatherDataService = generateWeatherDataService;
  }

  @Override
  public void run(String... args) throws Exception {
    generateWeatherDataService.refreshData();
  }
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
