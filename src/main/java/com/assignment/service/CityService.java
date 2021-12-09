package com.assignment.service;


import com.assignment.entity.City;
import com.assignment.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public Flux<City> getCityList() {
        return repository.findAll();
    }

    public Mono<City> getCityByName(String cityName) {
        return repository.findByName(cityName);
    }



    public Mono<?> updateCity(City updatedCity) {
        return repository.save(updatedCity); }

    public Mono<Void> deleteCityById(Long cityId) {
        return repository.deleteById(cityId);
    }

}
