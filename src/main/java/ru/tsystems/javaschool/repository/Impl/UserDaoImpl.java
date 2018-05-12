package ru.tsystems.javaschool.repository.Impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.User;
import ru.tsystems.javaschool.repository.AbstractDao;
import ru.tsystems.javaschool.repository.UserDao;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    public void save(User user) {
        persist(user);
    }

    public User findById(int id) {
        return getByKey(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findByLogin(String login) {
        Query query = getSession().createQuery("SELECT U FROM User U WHERE U.login = :login");
        query.setParameter("login", login);
        return (User) query.uniqueResult();
    }

}