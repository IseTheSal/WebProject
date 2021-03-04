package by.learning.web.validator;

public class OrderValidator {

    private static final String COUPON_CODE_REGEX = "[a-zA-Z0-9]{5,10}";
    private static final String GAMECODE_REGEX = "^([A-z0-9]{5}-)([A-z0-9]{5}-)([A-z0-9]{5})$";

    public static boolean isCouponValid(String couponCode) {
        boolean isValid = true;
        if ((couponCode == null) || (!couponCode.matches(COUPON_CODE_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isGameCodeValid(String gameCode) {
        boolean isValid = true;
        if ((gameCode == null) || (!gameCode.matches(GAMECODE_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

}
