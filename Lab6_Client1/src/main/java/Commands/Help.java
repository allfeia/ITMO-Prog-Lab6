package Commands;

import ConnectionUtils.Response;
import Data.Movie;
import Collection.CollectionManager;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Help implements Command, Serializable{

    @Serial
    private static final long serialVersionUID = 7L;

    private final Collection<Command> commands = new ArrayList<>();

    {
        commands.add(this);
        commands.add(new Info());
        commands.add(new Show());
        commands.add(new Add());
        commands.add(new UpdateId());
        commands.add(new RemoveById());
        commands.add(new Clear());
        commands.add(new ExecuteScript());
        commands.add(new Exit());
        commands.add(new PrintUniqueGenre());
        commands.add(new AddIfMax());
        commands.add(new AddIfMin());
        commands.add(new History());
    }

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args == null) throw new IllegalArgumentException("no arguments");
        return collectionManager.help(this.commands);
    }

    @Override
    public String description() {
        return "help: get information about all commands";
    }

    @Override
    public String getName(){
        return "help";
    }

}

