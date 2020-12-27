package by.learning.web.controller.command;

import by.learning.web.controller.command.impl.LoginCommand;
import by.learning.web.controller.command.impl.LogoutCommand;
import by.learning.web.controller.command.impl.RegistrationCommand;
import by.learning.web.controller.command.impl.RemoveUserCommand;
import by.learning.web.model.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    REGISTRATION(new RegistrationCommand(new UserServiceImpl())),
    REMOVE_USER(new RemoveUserCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand());

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
