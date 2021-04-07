package by.learning.web.controller.command;

import by.learning.web.controller.command.impl.*;
import by.learning.web.model.service.impl.GameServiceImpl;
import by.learning.web.model.service.impl.OrderServiceImpl;
import by.learning.web.model.service.impl.UserServiceImpl;

/**
 * <pre>Enum represents all commands.</pre>
 *
 * @author Illia Aheyeu
 */
public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    REGISTRATION(new RegistrationCommand(new UserServiceImpl())),
    HOME(new HomeCommand(new GameServiceImpl())),
    OPEN_GAME(new OpenGameCommand(new GameServiceImpl())),
    LOGOUT(new LogoutCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    CHANGE_EMAIL(new ChangeEmailCommand(new UserServiceImpl())),
    CHANGE_PASSWORD(new ChangePasswordCommand(new UserServiceImpl())),
    FIND_ORDER_HISTORY(new FindOrderHistoryCommand(new OrderServiceImpl())),
    ADD_TO_CART(new AddToCartCommand(new OrderServiceImpl())),
    REMOVE_FROM_CART(new RemoveFromCartCommand(new OrderServiceImpl())),
    CHANGE_CART_AMOUNT(new ChangeCartAmountCommand(new OrderServiceImpl())),
    USE_PROMOCODE(new UsePromocodeCommand(new OrderServiceImpl())),
    MAKE_ORDER(new MakeOrderCommand(new OrderServiceImpl())),
    FIND_CODE_AMOUNT(new FindGameCodeAmount(new OrderServiceImpl())),
    CREATE_GAME(new CreateGameCommand(new GameServiceImpl())),
    OPEN_GAME_CREATOR(new OpenGameCreatorCommand(new GameServiceImpl())),
    ADD_GAME_CODE(new AddGameCodeCommand(new OrderServiceImpl())),
    OPEN_GAME_EDITOR(new OpenEditGameCommand(new GameServiceImpl())),
    EDIT_GAME(new EditGameCommand(new GameServiceImpl())),
    OPEN_COUPON_LIST(new OpenCouponListCommand(new OrderServiceImpl())),
    CREATE_COUPON(new CreateCouponCommand(new OrderServiceImpl())),
    EDIT_COUPON_AMOUNT(new EditCouponAmountCommand(new OrderServiceImpl())),
    DELETE_COUPON(new DeleteCouponCommand(new OrderServiceImpl())),
    OPEN_ORDER_LIST(new OpenOrderListCommand(new OrderServiceImpl())),
    OPEN_USER_LIST(new OpenUserListCommand(new UserServiceImpl())),
    ADD_ADMIN(new AddAdminCommand(new UserServiceImpl())),
    FORGOT_PASSWORD(new ForgotPasswordCommand(new UserServiceImpl())),
    OPEN_RESET_PASSWORD(new OpenResetPasswordCommand(new UserServiceImpl())),
    RESET_PASSWORD(new ResetPasswordCommand(new UserServiceImpl())),
    FILTER_GAME(new FilterGameCommand(new GameServiceImpl())),
    ORDER_GAMES(new OrderGameCommand(new GameServiceImpl()));

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
