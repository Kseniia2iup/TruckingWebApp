package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.City;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.CityDao;

import java.util.List;

@Repository("cityDao")
public class CityDaoImpl extends AbstractDao<Integer, City> implements CityDao {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
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
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<City> findAllCities() {
        Query query = getSession().createQuery("From City");
        return query.list();
    }
}
