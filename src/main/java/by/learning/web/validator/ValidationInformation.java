package by.learning.web.validator;

/**
 * Enum used to send feedback about data validation and operation performing
 */
public enum ValidationInformation {
    SUCCESS("success"),
    FAIL("fail"),
    INPUT_INCORRECT("incorrect input"),
    TITLE_INCORRECT("title is incorrect"),
    IMAGE_PATH_INCORRECT("path is incorrect"),
    DESCRIPTION_INCORRECT("description is incorrect"),
    PRICE_INCORRECT("price is incorrect"),
    TRAILER_LINK_INCORRECT("link is incorrect"),
    GAME_CODE_INCORRECT("game code is incorrect"),
    CODE_EXISTS("code exists"),
    GAME_ID_DOES_NOT_EXIST("game is incorrect"),
    COUPON_DISCOUNT_INCORRECT("discount is incorrect"),
    COUPON_CODE_INCORRECT("coupon code is incorrect"),
    COUPON_AMOUNT_INCORRECT("coupon amount is incorrect"),
    PASSWORDS_NOT_EQUAL("passwords not equal"),
    PASSWORD_INCORRECT("password is incorrect"),
    EMAIL_INCORRECT("email is incorrect"),
    LOGIN_INCORRECT("login is incorrect"),
    FIRSTNAME_INCORRECT("firstname is incorrect"),
    SECONDNAME_INCORRECT("secondname is incorrect"),
    LOGIN_OR_EMAIL_EXIST("This login or email already exists"),
    USER_NOT_FOUND("User not found"),
    CART_EMPTY("Cart is empty"),
    CART_AMOUNT_INVALID("Invalid cart amount"),
    USER_BALANCE_INVALID("Invalid user balance");

    private final String value;

    ValidationInformation(String value) {
        this.value = value;
    }

    public String getInfoValue() {
        return value;
    }
}
