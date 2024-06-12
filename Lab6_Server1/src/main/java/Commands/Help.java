package Commands;

import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;
import Utils.CommandManager;

import java.io.Serializable;

public class Help extends Command implements Serializable {
    private CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "help: get information about all commands");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException, IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(ResponseStatus.OK,
                String.join("\n",
                        commandManager.getCommands()
                                .stream().map(Command::getDescription).toList()) + "\n");
    }
}
