package Utils;

import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.CommandRuntimeException;
import Errors.ExitObligedException;
import Errors.IllegalArgumentsException;
import Errors.NoSuchCommandException;

public class RequestHandler {
    private CommandManager commandManager;

    /**
     * Класс RequestHandler обрабатывает запросы, используя CommandManager для выполнения команд.
     */

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Метод для обработки запросов.
     *
     * @param request объект Request, представляющий запрос.
     * @return объект Response, содержащий результат выполнения команды.
     */

    public Response handle(Request request) {
        try {
            return commandManager.execute(request);

        } catch (IllegalArgumentsException e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,
                    "Illegal using of command's arguments");
        } catch (CommandRuntimeException e) {
            return new Response(ResponseStatus.ERROR,
                    "Error with executing app");
        } catch (NoSuchCommandException e) {
            return new Response(ResponseStatus.ERROR, "There is no such command");
        } catch (ExitObligedException e) {
            return new Response(ResponseStatus.EXIT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
