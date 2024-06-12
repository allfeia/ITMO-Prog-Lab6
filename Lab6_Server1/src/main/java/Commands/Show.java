package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;

import java.io.Serial;
import java.io.Serializable;

public class Show extends Command implements Serializable {
    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "show: get all elements in collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (CollectionManager.getMovies() == null || CollectionManager.getMovies().isEmpty()) {
            return new Response(ResponseStatus.ERROR, "The collection has not been initialized yet");
        }
        return new Response(ResponseStatus.OK, "Collection: \n" + collectionManager.show() + "\n");
    }
}
