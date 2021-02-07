package by.learning.web.controller.command;

import by.learning.web.controller.command.impl.*;
import by.learning.web.model.service.impl.GameServiceImpl;
import by.learning.web.model.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    REGISTRATION(new RegistrationCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    HOME(new HomeCommand(new GameServiceImpl())),
    OPEN_GAME(new OpenGameCommand(new GameServiceImpl())),
    ADD_TO_CART(new AddToCartCommand(new GameServiceImpl()));

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
