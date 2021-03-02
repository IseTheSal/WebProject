package by.learning.web.controller.command;

import by.learning.web.controller.command.impl.*;
import by.learning.web.model.service.impl.GameServiceImpl;
import by.learning.web.model.service.impl.OrderServiceImpl;
import by.learning.web.model.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    REGISTRATION(new RegistrationCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    HOME(new HomeCommand(new GameServiceImpl())),
    OPEN_GAME(new OpenGameCommand(new GameServiceImpl())),
    ADD_TO_CART(new AddToCartCommand(new OrderServiceImpl())),
    REMOVE_FROM_CART(new RemoveFromCartCommand(new OrderServiceImpl())),
    CHANGE_CART_AMOUNT(new ChangeCartAmountCommand(new OrderServiceImpl())),
    USE_PROMOCODE(new UsePromocodeCommand(new OrderServiceImpl())),
    MAKE_ORDER(new MakeOrderCommand(new OrderServiceImpl())),
    CHANGE_EMAIL(new ChangeEmailCommand(new UserServiceImpl())),
    CHANGE_PASSWORD(new ChangePasswordCommand(new UserServiceImpl())),
    FIND_ORDER_HISTORY(new FindOrderHistoryCommand(new OrderServiceImpl())),
    FIND_CODE_AMOUNT(new FindGameCodeAmount(new OrderServiceImpl())),
    CREATE_GAME(new CreateGameCommand(new GameServiceImpl())),
    OPEN_GAME_CREATOR(new OpenGameCreatorCommand(new GameServiceImpl()));

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
