package ru.tsystems.javaschool.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.tsystems.javaschool.model.City;
import ru.tsystems.javaschool.service.CityService;

@Component
public class CityIdToCityConverter implements Converter<Object, City> {

    private static final Logger LOG = LoggerFactory.getLogger(CityIdToCityConverter.class);

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public City convert(Object source) {
        Integer id = Integer.parseInt((String)source);
        City city = cityService.findCityById(id);
        LOG.info("From CityIdToCityConverter convert method\nProfile : {}", city);
        return city;
    }
}
