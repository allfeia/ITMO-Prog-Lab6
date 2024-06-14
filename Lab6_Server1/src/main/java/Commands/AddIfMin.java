package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class AddIfMin implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args != null)
            throw new IllegalArgumentException("no arguments except movie's parametrs");
        if (movie == null) throw new IllegalArgumentException("enter movie's parametrs");
        return collectionManager.add(movie);
    }
    @Override
    public String description() {
        return "add_if_min {element}: add new movie if oscars count in it less then in any in collection";
    }

    @Override
    public String getName(){
        return "add_if_min";
    }
}
