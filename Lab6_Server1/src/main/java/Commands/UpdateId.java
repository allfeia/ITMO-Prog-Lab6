package Commands;

import Collection.CollectionManager;
import Collection.CollectionUtil;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UpdateId implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 14L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args == null)
            throw new NullPointerException("enter argument - id");
        Integer id;
        try {
            id = Integer.parseInt((String) args);
        } catch (Exception e) {
            throw new IllegalArgumentException("argument id must be integer");
        }
        return collectionManager.updateId(movie, id);
    }

    @Override
    public String description() {
        return "update {element}: update element in collection by its id";
    }

    @Override
    public String getName(){
        return "update";
    }
}

