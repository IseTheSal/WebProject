package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserWarehouse {

    private static UserWarehouse instance;
    private List<User> userList = new ArrayList<>();

    public static UserWarehouse getInstance() {
        if (instance == null) {
            instance = new UserWarehouse();
        }
        return instance;
    }

    public int size() {
        return userList.size();
    }

    public boolean add(User user) {
        return userList.add(user);
    }

    public User get(int index) throws DaoException {
        if (index < 0 || index >= userList.size()) {
            throw new DaoException("Index - " + index + " goes out of List");
        }
        return userList.get(index);
    }

    public User set(int index, User element) throws DaoException {
        if (index < 0) {
            throw new DaoException("Index " + index + " is less than 0");
        }
        return userList.set(index, element);
    }

    public void add(int index, User element) throws DaoException {
        if (index < 0) {
            throw new DaoException("Index " + index + " is less than 0");
        }
        userList.add(index, element);
    }

    public Optional<User> findUser(String login, String password) {
        Optional<User> result = Optional.empty();
        int i = 0;
        while (i < userList.size()) {
            User user = userList.get(i);
            if (user.getPassword().equals(password)
                    && user.getLogin().equals(login)) {
                result = Optional.of(user);
                break;
            }
            i++;
        }
        return result;
    }

    public boolean removeById(int id){
        boolean isRemoved = false;
        int i = 0;
        while (i < userList.size()) {
            User user = userList.get(i);
            if (user.getId() == id) {
                isRemoved = userList.remove(user);
                break;
            }
            i++;
        }
        return isRemoved;
    }
}
