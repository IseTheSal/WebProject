package by.learning.web.controller.command;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * <pre>Class used to define command.</pre>
 *
 * @author Illia Aheyeu
 */
public class CommandProvider {
    private static final Logger logger = LogManager.getLogger();

    private CommandProvider() {
    }

    /**
     * Defines ActionCommand from {@link CommandType CommandType}.
     *
     * @param commandName String value of command
     * @return Optional action command
     */
    public static Optional<ActionCommand> defineCommand(String commandName) {
        if (commandName == null || commandName.isBlank()) {
            return Optional.empty();
        }
        Optional<ActionCommand> current = Optional.empty();
        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            current = Optional.of(type.getCommand());
        } catch (IllegalArgumentException ex) {
            logger.log(Level.ERROR, "Incorrect command was provided", ex);
        }
        return current;
    }
}
