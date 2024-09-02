package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class PrintUniqueGenre implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 10L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args == null) throw new IllegalArgumentException("no arguments");
        return collectionManager.printUniqueMovieGenre();
    }

    @Override
    public String description() {
        return "print_unique_genre: get unique genres in collection";
    }

    @Override
    public String getName(){
        return "print_unique_genre";
    }

}

