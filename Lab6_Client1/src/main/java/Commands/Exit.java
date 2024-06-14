package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class Exit implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 5L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        return new Response("Do svidanya!");
    }

    @Override
    public String description() {
        return "exit: end app with saving collection into file";
    }

    @Override
    public String getName(){
        return "exit";
    }
}

