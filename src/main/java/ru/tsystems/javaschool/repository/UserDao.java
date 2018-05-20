package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    void delete(Integer id);

    User findById(int id);

    User findByLogin(String login);

    List<User> findAllUsers();
}
