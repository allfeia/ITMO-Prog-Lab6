package Utils;

import Commands.Command;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;
import Collection.CollectionManager;

/**
 * Класс инициатора
 * инициатор - сущность запускающая команды
 */

public class CommandManager {

    public static Response runCommand(Request request, CollectionManager collectionManager) {
        Command command = request.getCommand();
        Object args = request.getArgs();
        Movie movie = request.getObject();
        try {
            return command.execute(args, movie, collectionManager);
        } catch (Exception e) {
            return new Response("Error with executing command " + e.getMessage());
        }
    }

}
