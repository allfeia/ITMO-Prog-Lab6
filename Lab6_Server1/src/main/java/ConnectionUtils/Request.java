package ConnectionUtils;

import Data.Movie;

import java.io.*;
import java.util.Objects;

/**
 * Класс Request представляет собой запрос, который может содержать команду, аргументы и объект типа Movie.
 * Он имплементирует интерфейс Serializable, что позволяет сериализовать его объекты.
 */

public class Request implements Serializable {
    private String commandName;
    private String args = "";
    private Movie object = null;

    /**
     * Конструктор для создания запроса с командой и объектом типа Movie.
     * @param ok Статус ответа (не используется внутри конструктора)
     * @param commandName Название команды
     * @param object Объект типа Movie
     */

    public Request(ResponseStatus ok, String commandName, Movie object) {
        this.commandName = commandName.trim();
    }

    /**
     * Конструктор для создания запроса с командой и аргументами.
     * @param commandName Название команды
     * @param args Аргументы команды
     */

    public Request(String commandName, String args) {
        this.commandName = commandName.trim();
        this.args = args;
    }

    /**
     * Конструктор для создания запроса с командой и объектом типа Movie.
     * @param commandName Название команды
     * @param object Объект типа Movie
     */

    public Request(String commandName, Movie object) {
        this.commandName = commandName.trim();
        this.object = object;
    }

    /**
     * Конструктор для создания запроса с командой, аргументами и объектом типа Movie.
     * @param commandName Название команды
     * @param args Аргументы команды
     * @param object Объект типа Movie
     */

    public Request(String commandName, String args, Movie object) {
        this.commandName = commandName.trim();
        this.args = args.trim();
        this.object = object;
    }

    public boolean isEmpty() {
        return commandName.isEmpty() && args.isEmpty() && object == null;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }
    public Movie getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return Objects.equals(commandName, request.commandName) && Objects.equals(args, request.args) && Objects.equals(object, request.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandName, args, object);
    }

    @Override
    public String toString(){
        return "Request[" + commandName +
                (args.isEmpty()
                        ? ""
                        : "," + args ) +
                ((object == null)
                        ? "]"
                        : "," + object + "]");
    }

}
