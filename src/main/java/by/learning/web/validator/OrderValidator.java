package by.learning.web.validator;

public class OrderValidator {

    private static final String COUPON_CODE_REGEX = "[a-zA-Z0-9]{5,10}";

    public static boolean isCouponValid(String code){
        boolean isValid = true;
        if((code == null) || (!code.matches(COUPON_CODE_REGEX))){
            isValid = false;
        }
        return isValid;
    }
}
