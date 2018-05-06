package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.User;

public interface UserService {
    User getUser(String login);
}
