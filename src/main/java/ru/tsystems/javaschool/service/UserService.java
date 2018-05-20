package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void updateUser(User user);

    void delete(Integer id);

    User findById(int id);

    User findByLogin(String login);

    boolean isUserValid(User user);

    boolean isUserLoginUnique(String login);

    List<User> findAllUsers();
}
