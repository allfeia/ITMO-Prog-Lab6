package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class Add implements Command, Serializable {

    @Serial
    private static final  long serialVersionUID = 0L;


    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args != null)
            throw new IllegalArgumentException("no arguments except movie's parametrs");
        if (movie == null) throw new IllegalArgumentException("enter movie's parametrs");
        return collectionManager.add(movie);
    }
    @Override
    public String description() {
        return "add {element}: add movie into collection";
    }

    @Override
    public String getName(){
        return "add";
    }
}


