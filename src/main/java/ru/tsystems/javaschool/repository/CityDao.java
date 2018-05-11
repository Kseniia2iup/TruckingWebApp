package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.City;

import java.util.List;

public interface CityDao {

    City findCityById(int id);

    City findCityByName(String name);

    List<City> findAllCities();
}
