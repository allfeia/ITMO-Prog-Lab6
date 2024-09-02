package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class History implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 8L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args == null) throw new IllegalArgumentException("no arguments");
        return collectionManager.history();
    }

    @Override
    public String description() {
        return "history: get last 7 commands";
    }

    @Override
    public String getName(){
        return "history";
    }
}
