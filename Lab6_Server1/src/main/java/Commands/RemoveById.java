package Commands;

import Collection.CollectionManager;
import Collection.CollectionUtil;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class RemoveById implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 11L;

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
        return collectionManager.removeById(id);
    }

    @Override
    public String description() {
        return "remove_by_id {id}: remove element from collection by its id";
    }

    @Override
    public String getName(){
        return "remove_by_id";
    }
}

