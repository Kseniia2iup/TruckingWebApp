package ru.tsystems.javaschool.service;

import ru.tsystems.javaschool.model.UserProfile;

import java.util.List;

public interface UserProfileService {

    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
