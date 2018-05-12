package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.User;
import ru.tsystems.javaschool.repository.UserDao;
import ru.tsystems.javaschool.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao dao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    public User findById(int id) {
        return dao.findById(id);
    }

    public User findByLogin(String login) {
        return dao.findByLogin(login);
    }

}
