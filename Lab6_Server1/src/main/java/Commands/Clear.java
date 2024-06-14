package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class Clear implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args == null) throw new IllegalArgumentException("no arguments");
        return collectionManager.clear();
    }

    @Override
    public String description() {
        return "clear: clear collection";
    }

    @Override
    public String getName(){
        return "clear";
    }
}
