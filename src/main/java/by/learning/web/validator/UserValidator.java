package by.learning.web.validator;

import java.util.EnumSet;
import java.util.Set;

/**
 * <pre>Check {@link by.learning.web.model.entity.User User} input data.</pre>
 *
 * @author Illia Aheyeu
 */
public class UserValidator {
    private static final String LOGIN_REGEX = "^[a-z0-9]([_](?![_])|[a-zA-Z0-9]){4,10}[a-z0-9]$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{8,20}$";
    private static final String NAME_REGEX = "^[A-Za-z|А-я]{2,20}$";
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private UserValidator() {
    }

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

    public static Set<ValidationInformation> isUserValid(String firstName, String lastName, String login, String password, String repeatPassword, String email) {
        Set<ValidationInformation> issues = EnumSet.noneOf(ValidationInformation.class);
        if (!password.equals(repeatPassword)) {
            issues.add(ValidationInformation.PASSWORDS_NOT_EQUAL);
        }
        if (!isPasswordValid(password)) {
            issues.add(ValidationInformation.PASSWORD_INCORRECT);
        }
        if (!isLoginValid(login)) {
            issues.add(ValidationInformation.LOGIN_INCORRECT);
        }
        if (!isEmailValid(email)) {
            issues.add(ValidationInformation.EMAIL_INCORRECT);
        }
        if (!isNameValid(firstName)) {
            issues.add(ValidationInformation.FIRSTNAME_INCORRECT);
        }
        if (!isNameValid(lastName)) {
            issues.add(ValidationInformation.SECONDNAME_INCORRECT);
        }
        if (!issues.isEmpty()) {
            issues.add(ValidationInformation.FAIL);
        }
        return issues;
    }
}
