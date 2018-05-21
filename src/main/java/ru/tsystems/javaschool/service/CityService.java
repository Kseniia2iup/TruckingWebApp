package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.City;

import java.util.List;

public interface CityService {

    City findCityById(int id);

    City findCityByName(String name);

    List<City> findAllCities();

    Double distanceBetweenTwoCities(City cityA, City cityB);
}
