
package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Response;
import Data.Movie;

public interface Command {
    Response execute(Object args, Movie movie, CollectionManager collectionManager);

    String description();
    String getName();
}
