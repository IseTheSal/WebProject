package by.learning.web.model.service.impl;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.impl.UserDaoImpl;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
import by.learning.web.validator.UserValidator;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl {

    public UserServiceImpl() {

    }

    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    public Optional<User> singIn(String login, String password) {
        Optional<User> result = Optional.empty();
        if (UserValidator.isLoginValid(login)
                && UserValidator.isPasswordValid(password)) {
            result = userDao.findUser(login, password);
        }
        return result;
    }

    public void addUser(User user) {
        userDao.add(user);
    }

    public int findUserIndex(User user) throws ServiceException {
        Optional<Integer> userIndex = userDao.findUserIndex(user);
        if (userIndex.isEmpty()) {
            throw new ServiceException("This user does not exist");
        }
        return userIndex.get();

    }

    public boolean removeUserById(int id) {
        return userDao.removeById(id);
    }

    public List<User> findAllUsers() {
        List<User> result = userDao.findAll();
        return result;
    }
}
