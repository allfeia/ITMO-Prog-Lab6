package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class GroupCountingByName implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 6L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args == null) throw new IllegalArgumentException("no arguments");
        return collectionManager.clear();
    }

    @Override
    public String description() {
        return "group_counting_by_name: get grouped movies by name and their count";
    }

    @Override
    public String getName(){
        return "group_counting_by_name";
    }
}
