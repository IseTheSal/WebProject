package by.learning.web.model.entity;

import by.learning.web.util.IdGenerator;

public class User {
    private long id = IdGenerator.getUserId();
    private String name;
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        User user = (User) o;

        if (getLogin() != null ? !(user.getLogin().equals(getLogin())) : !(user.getLogin() == null)) {
            return false;
        }
        return (getPassword() != null ? !(user.getPassword().equals(getLogin())) : !(user.getPassword() == null));
    }


    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
