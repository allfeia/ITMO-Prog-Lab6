package ConnectionUtils;

import Commands.Command;
import Data.Movie;

import java.io.*;
import java.util.Objects;

/**
 * Request - это класс запроса, который клиент отправляет на сервер, содержит объект команды и ее аргументы
 */

public class Request implements Serializable {

    private static final long serialVersionUID = 666L;
    private Command command;
    private String args = "";
    private Movie object;

    public Request(Command command, String args, Movie object) {
        this.command = command;
        this.args = args;
        this.object = object;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Movie getObject() {
        return object;
    }

    public void setObject(Movie object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return Objects.equals(command, request.command) && Objects.equals(args, request.args) && Objects.equals(object, request.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, args, object);
    }

    @Override
    public String toString(){
        return "Request[" + command +
                (args.isEmpty()
                        ? ""
                        : "," + args ) +
                ((object == null)
                        ? "]"
                        : "," + object + "]");
    }

}
