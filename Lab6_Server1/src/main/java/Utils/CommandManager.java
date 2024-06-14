package Utils;

import Commands.Command;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;
import Collection.CollectionManager;

/**
 * Класс CommandManager управляет командами и отвечает за их выполнение.
 * Он хранит коллекцию команд и предоставляет методы для добавления новых команд,
 * выполнения команд на основе запросов и получения всех доступных команд.
 */

public class CommandManager {
    public Response runCommand(Request request, CollectionManager collectionManager) {
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
