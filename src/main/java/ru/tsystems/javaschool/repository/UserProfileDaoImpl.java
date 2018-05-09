package ru.tsystems.javaschool.repository;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.UserProfile;

import java.util.List;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>implements UserProfileDao{

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserProfile> findAll(){
        Query query = getSession().createQuery("from UserProfile");
        return query.list();
    }

    public UserProfile findById(int id) {
        return getByKey(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserProfile findByType(String type) {
        Query query = getSession().createQuery("SELECT U FROM UserProfile U WHERE U.type = :type");
        query.setParameter("type", type);
        return (UserProfile) query.uniqueResult();
    }
}