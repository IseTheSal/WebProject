package by.learning.web.model.dao.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.model.dao.UserDao;
import by.learning.web.model.dao.UserWarehouse;
import by.learning.web.model.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl instance;
    private UserWarehouse userWarehouse = UserWarehouse.getInstance();

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> findUser(String login, String password) {
        return userWarehouse.findUser(login, password);
    }

    @Override
    public void add(User user){
        userWarehouse.add(user);
    }

    @Override
    public boolean removeById(int id) {
        return userWarehouse.removeById(id);
    }


    @Override
    public List<User> findAll() {
        return null;
    }

    public Optional<Integer> findUserIndex(User user) {
        return Optional.empty();
    }
}
