package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.User;

public interface UserDao {

    void save(User user);

    User findById(int id);

    User findByLogin(String login);
}
