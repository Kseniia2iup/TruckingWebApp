package ru.tsystems.javaschool.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.javaschool.model.User;
import ru.tsystems.javaschool.repository.UserDao;
import ru.tsystems.javaschool.service.UserService;

import java.util.List;

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

    @Override
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setLogin(user.getLogin());
            entity.setPassword(user.getPassword());
            //entity.setEmail(user.getEmail());
            entity.setRole(user.getRole());
        }
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    public User findById(int id) {
        return dao.findById(id);
    }

    public User findByLogin(String login) {
        return dao.findByLogin(login);
    }

    @Override
    public boolean isUserValid(User user) {
        return (user.getLogin()!=null && user.getPassword()!=null
                && user.getLogin().length()>=3
                && user.getPassword().length()>=5
                && isUserLoginUnique(user.getLogin()));
    }

    @Override
    public boolean isUserLoginUnique(String login) {
        return findByLogin(login) == null;
    }

    @Override
    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }
}
