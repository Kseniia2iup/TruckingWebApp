package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.tsystems.javaschool.model.City;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.CityDao;

import java.util.List;

@Repository("cityDao")
public class CityDaoImpl extends AbstractDao<Integer, City> implements CityDao {

    @Override
    public City findCityById(int id) {
        return getByKey(id);
    }

    @Override
    public City findCityByName(String name) {
        Query query = getSession().createQuery("SELECT C FROM City C WHERE C.name = :name");
        query.setParameter("name", name);
        return (City) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<City> findAllCities() {
        Query query = getSession().createQuery("From City");
        return query.list();
    }
}
