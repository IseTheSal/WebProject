package by.learning.web.validator;

public class UserValidator {

    private final static String LOGIN_REGEX = "[a-z0-9]{6,15}";
    private final static String PASSWORD_REGEX = "[a-zA-Z0-9]{8,20}";
    private final static String NAME_REGEX = "^[A-Z|a-z]{1,20}$";


    public static boolean isLoginValid(String login) {
        boolean isValid = true;
        if ((login == null) || (!login.matches(LOGIN_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isPasswordValid(String password) {
        boolean isValid = true;
        if ((password == null) || (!password.matches(PASSWORD_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isNameValid(String name) {
        boolean isValid = true;
        if ((name == null) || (!name.matches(NAME_REGEX))) {
            isValid = false;
        }
        return isValid;
    }
}
