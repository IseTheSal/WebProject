package by.learning.web.validator;

import java.util.EnumSet;
import java.util.Set;

/**
 * <pre>Check {@link by.learning.web.model.entity.ClientOrder Order} input data.</pre>
 *
 * @author Illia Aheyeu
 */
public class OrderValidator {

    private static final String COUPON_CODE_REGEX = "[a-zA-Z0-9]{5,10}";
    private static final String GAMECODE_REGEX = "^([A-z0-9]{5}-)([A-z0-9]{5}-)([A-z0-9]{5})$";
    private static final String COUPON_DISCOUNT_REGEX = "^[1-9][0-9]?$";
    private static final String COUPON_AMOUNT_REGEX = "^[1-9][0-9]{0,5}$";

    public static boolean isGameCodeValid(String gameCode) {
        boolean isValid = true;
        if ((gameCode == null) || (!gameCode.matches(GAMECODE_REGEX))) {
            isValid = false;
        }
        return isValid;
    }


    public static boolean isCouponCodeValid(String couponCode) {
        boolean isValid = true;
        if ((couponCode == null) || (!couponCode.matches(COUPON_CODE_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isCouponDiscountValid(String discount) {
        boolean isValid = true;
        if ((discount == null) || (!discount.matches(COUPON_DISCOUNT_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isCouponAmountValid(String amount) {
        boolean isValid = true;
        if ((amount == null) || (!amount.matches(COUPON_AMOUNT_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static Set<ValidationInformation> isCouponValid(String code, String discount, String amount) {
        Set<ValidationInformation> issues = EnumSet.noneOf(ValidationInformation.class);
        if (!isCouponCodeValid(code)) {
            issues.add(ValidationInformation.COUPON_CODE_INCORRECT);
        }
        if (!isCouponDiscountValid(discount)) {
            issues.add(ValidationInformation.COUPON_DISCOUNT_INCORRECT);
        }
        if (!isCouponAmountValid(amount)) {
            issues.add(ValidationInformation.COUPON_AMOUNT_INCORRECT);
        }
        if (!issues.isEmpty()) {
            issues.add(ValidationInformation.FAIL);
        }
        return issues;
    }

}
