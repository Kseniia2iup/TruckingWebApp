package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.City;
import ru.tsystems.javaschool.repository.CityDao;
import ru.tsystems.javaschool.service.CityService;

import java.util.List;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {

    private CityDao cityDao;

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public City findCityById(int id) {
        return cityDao.findCityById(id);
    }

    @Override
    public City findCityByName(String name) {
        return cityDao.findCityByName(name);
    }

    @Override
    public List<City> findAllCities() {
        return cityDao.findAllCities();
    }

    @Override
    public Double distanceBetweenTwoCity(City cityA, City cityB) {

        if(cityA.equals(cityB)){
            return 0d;
        }

        Double latitudeA = cityA.getLatitude();
        Double longitudeA = cityA.getLongitude();
        Double latitudeB = cityB.getLatitude();
        Double longitudeB = cityB.getLongitude();

        Double earthRadius = 6371d;

        return earthRadius*2*Math.asin(Math.sqrt(Math.pow(Math.sin((
                latitudeA-Math.abs(latitudeB))*Math.PI/180/2),2)+Math.cos(latitudeA*Math.PI/180)
                *Math.cos(Math.abs(latitudeB)*Math.PI/180)*Math.pow(Math.sin((longitudeA-longitudeB)
                *Math.PI/180/2),2)));
    }
}
