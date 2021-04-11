package by.learning.web.controller.command;

import by.learning.web.controller.command.impl.*;

/**
 * <pre>Enum represents all commands.</pre>
 *
 * @author Illia Aheyeu
 */
public enum CommandType {
    LOGIN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    HOME(new HomeCommand()),
    OPEN_GAME(new OpenGameCommand()),
    LOGOUT(new LogoutCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    CHANGE_EMAIL(new ChangeEmailCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    FIND_ORDER_HISTORY(new FindOrderHistoryCommand()),
    ADD_TO_CART(new AddToCartCommand()),
    REMOVE_FROM_CART(new RemoveFromCartCommand()),
    CHANGE_CART_AMOUNT(new ChangeCartAmountCommand()),
    USE_PROMOCODE(new UsePromocodeCommand()),
    MAKE_ORDER(new MakeOrderCommand()),
    FIND_CODE_AMOUNT(new FindGameCodeAmount()),
    CREATE_GAME(new CreateGameCommand()),
    OPEN_GAME_CREATOR(new OpenGameCreatorCommand()),
    ADD_GAME_CODE(new AddGameCodeCommand()),
    OPEN_GAME_EDITOR(new OpenEditGameCommand()),
    EDIT_GAME(new EditGameCommand()),
    OPEN_COUPON_LIST(new OpenCouponListCommand()),
    CREATE_COUPON(new CreateCouponCommand()),
    EDIT_COUPON_AMOUNT(new EditCouponAmountCommand()),
    DELETE_COUPON(new DeleteCouponCommand()),
    OPEN_ORDER_LIST(new OpenOrderListCommand()),
    OPEN_USER_LIST(new OpenUserListCommand()),
    ADD_ADMIN(new AddAdminCommand()),
    FORGOT_PASSWORD(new ForgotPasswordCommand()),
    OPEN_RESET_PASSWORD(new OpenResetPasswordCommand()),
    RESET_PASSWORD(new ResetPasswordCommand()),
    FILTER_GAME(new FilterGameCommand()),
    ORDER_GAMES(new OrderGameCommand()),
    INCREASE_BALANCE(new IncreaseBalanceCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    /**
     * Get commands.
     *
     * @return command.
     */
    public ActionCommand getCommand() {
        return command;
    }
}
