package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.User;

public interface UserService {

    void save(User user);

    User findById(int id);

    User findByLogin(String login);
}
