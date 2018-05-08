package ru.tsystems.javaschool.repository;

import ru.tsystems.javaschool.model.UserProfile;

import java.util.List;

public interface UserProfileDao {

    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
