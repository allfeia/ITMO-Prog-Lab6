package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;

import java.io.Serializable;

public class PrintUniqueGenre extends Command implements Serializable {
    private final CollectionManager collectionManager;

    public PrintUniqueGenre(CollectionManager collectionManager) {
        super("print_unique_genre", "print_unique_genre: get unique genres in collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (CollectionManager.getMovies() == null || CollectionManager.getMovies().isEmpty()) {
            return new Response(ResponseStatus.ERROR, "The collection has not been initialized yet");
        }
        return new Response(ResponseStatus.OK, "Unique genres: " + collectionManager.printUniqueMovieGenre() + "\n");
    }

}
