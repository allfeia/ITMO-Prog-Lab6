package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;

import java.io.Serial;
import java.io.Serializable;

public class Info extends Command implements Serializable {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "info: get information about collection (type, data of initialization, count of elements)");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(ResponseStatus.OK, collectionManager.info() + "\n");
    }
}
