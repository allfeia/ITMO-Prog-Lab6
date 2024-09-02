package ConnectionUtils;

import Commands.*;
import Forms.MovieAppender;
import Utils.ScriptRunner;

import java.io.*;
import java.util.*;

/**
 * Класс клиент
 * объект класса представляет консоль для клиента считывающую команды
 */


public class Client {
    private final Map<String, Command> commandMap = new HashMap<>();
    {
        commandMap.put("help", new Help());
        commandMap.put("info", new Info());
        commandMap.put("show", new Show());
        commandMap.put("add", new Add());
        commandMap.put("update", new UpdateId());
        commandMap.put("remove_by_id", new RemoveById());
        commandMap.put("clear", new Clear());
        commandMap.put("execute_script", new ExecuteScript());
        commandMap.put("exit", new Exit());
        commandMap.put("print_unique_genre", new PrintUniqueGenre());
        commandMap.put("sum_of_oscars_count", new SumOfOscarsCount());
        commandMap.put("group_counting_by_name", new GroupCountingByName());
        commandMap.put("add_if_max", new AddIfMax());
        commandMap.put("add_if_min", new AddIfMin());
        commandMap.put("history", new History());
    }

    /**
     * Метод запускающий консоль
     * @param inStream поток ввода (стандартный или файловый)
     * @param sender отправитель запросов на сервер
     * @param fileFlag флаг истинный если выполняется команда execute_script и ложный в противном случае
     */

    public void runApp(InputStreamReader inStream, Sender sender, Boolean fileFlag){
        Scanner in = new Scanner(inStream);
        while (in.hasNextLine()){
            try {
                //Чтение и обработка ввода
                var input = in.nextLine();
                if (input.isBlank()) {throw new NullPointerException("You didn't entered anything");}
                List<String> commandWithArgs = List.of(input.split(" "));
                String commandName = commandWithArgs.get(0).toLowerCase();
                List<String> commandArguments = null;
                if (commandWithArgs.size() >= 2){
                    commandArguments = commandWithArgs.subList(1, commandWithArgs.size());
                }
                if (commandMap.get(commandName) == null)
                    throw new IllegalArgumentException("There is no such command");

                //Построение запроса на сервер
                var request = new Request();
                request.setCommand(commandMap.get(commandName));
                if (commandArguments != null)request.setArgs(String.join(" ",  commandArguments));
                if (List.of("add", "add_if_max", "add_if_min", "update").contains(commandName)) {
                    if (fileFlag){
                        request.setObject(MovieAppender.appendMovie(in.nextLine()));
                    } else request.setObject(MovieAppender.appendMovie());
                }

                //Получение и вывод ответа сервера
                var response = sender.sendRequest(request);
                try {
                    System.out.println(response.getResult());
                } catch (NullPointerException e) {
                    System.err.println("no response from server(");
                }

                if (commandName.equals("exit")) {
                    System.exit(0);
                }
                if (commandName.equals("execute_script")) {
                    try {
                        var output = List.of(response.getResult().split(" "));
                        var filename = output.get(output.size() - 1);
                        var scriptRunner = new ScriptRunner();
                        scriptRunner.runScript(filename);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            } catch (NoSuchElementException e) {
                System.err.println("you make me upset.. bye");
                System.exit(0);
            }
            catch (Exception e){
                if (!fileFlag)
                    System.err.println(e.getMessage());
            }
        }
    }


}
