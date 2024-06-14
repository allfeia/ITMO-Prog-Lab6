package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class SumOfOscarsCount implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 13L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args == null) throw new IllegalArgumentException("no arguments");
        return collectionManager.sumOfOscarsCount();
    }

    @Override
    public String description() {
        return "sum_of_oscars_count: get sum of oscars count from all movies in collection";
    }

    @Override
    public String getName(){
        return "sum_of_oscars_count";
    }

}

