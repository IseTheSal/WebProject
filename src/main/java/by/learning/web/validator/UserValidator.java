package by.learning.web.validator;

public class UserValidator {

    private static final String LOGIN_REGEX = "^[a-z0-9]([_](?![_])|[a-zA-Z0-9]){4,10}[a-z0-9]$";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9]{8,20}";
    private static final String NAME_REGEX = "^[A-Za-z|А-я]{2,20}$";
    private static final String EMAIL_REGEX = "[a-zA-Z0-9]+[._a-zA-Z0-9]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}";


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

    public static boolean isEmailValid(String email) {
        boolean isValid = true;
        if ((email == null) || (!email.matches(EMAIL_REGEX))) {
            isValid = false;
        }
        return isValid;
    }
}
