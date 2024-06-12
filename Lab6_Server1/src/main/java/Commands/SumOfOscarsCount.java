package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;

import java.io.Serializable;

public class SumOfOscarsCount extends Command implements Serializable {
    private final CollectionManager collectionManager;
    public SumOfOscarsCount(CollectionManager collectionManager){
        super("sum_of_oscars_count", "sum_of_oscars_count: get sum of oscars count from all movies in collection");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Request request) throws IllegalArgumentException{
        if (!request.getArgs().isBlank()) throw new IllegalArgumentException();
            return new Response(ResponseStatus.OK, collectionManager.sumOfOscarsCount());
    }

}
