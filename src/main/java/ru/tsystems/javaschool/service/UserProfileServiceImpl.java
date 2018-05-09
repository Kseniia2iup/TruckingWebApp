package ru.tsystems.javaschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.UserProfile;
import ru.tsystems.javaschool.repository.UserProfileDao;

import java.util.List;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService{

    private UserProfileDao dao;

    @Autowired
    public void setDao(UserProfileDao dao) {
        this.dao = dao;
    }

    public List<UserProfile> findAll() {
        return dao.findAll();
    }

    public UserProfile findByType(String type){
        return dao.findByType(type);
    }

    public UserProfile findById(int id) {
        return dao.findById(id);
    }
}