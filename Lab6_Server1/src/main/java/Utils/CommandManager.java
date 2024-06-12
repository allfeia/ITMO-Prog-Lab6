package Utils;

import Commands.Command;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Errors.NoSuchCommandException;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Класс CommandManager управляет командами и отвечает за их выполнение.
 * Он хранит коллекцию команд и предоставляет методы для добавления новых команд,
 * выполнения команд на основе запросов и получения всех доступных команд.
 */

public class CommandManager {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final Parser parser;

    public CommandManager(Parser parser) {
        this.parser = parser;
    }

    public void addCommand(Command command){
        this.commands.put(command.getName(), command);
        ServerLogger.getLogger().info("Command added: " + command);
    }
    public void addCommand(Collection<Command> commands){
        this.commands.putAll(commands.stream()
                .collect(Collectors.toMap(Command::getName, s -> s)));
        ServerLogger.getLogger().info("Commands added: " + commands);
    }

    /**
     * Выполняет команду на основе запроса.
     *
     * @param request Запрос, содержащий данные для выполнения команды
     * @return Ответ на запрос
     * @throws Exception Если команда не найдена или происходит ошибка выполнения
     */

    public Collection<Command> getCommands(){
        return commands.values();
    }
    public Response execute(Request request) throws Exception {
        Command command = commands.get(request.getCommandName());
        if (command == null) {
            ServerLogger.getLogger().warning("There is no such command" + request.getCommandName());
            throw new NoSuchCommandException();
        }
        // Выполняем команду и получаем ответ
        Response response = command.execute(request);
        ServerLogger.getLogger().info("Executing command\n" + response);
        // Если команда является редактором коллекции, логируем обновление файла и сохраняем данные
//        if (command instanceof CollectionEditor) {
//            ServerLogger.getLogger().info("File has been updated");
//            Parser.saveToCSV();
//        }
        return response;
    }


}
