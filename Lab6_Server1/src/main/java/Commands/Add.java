package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;

import java.io.Serializable;
import java.util.Objects;

public class Add extends Command implements Serializable {
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add", "add {element}: add new movie into collection");
        this.collectionManager = collectionManager;
    }

    /**
     * add a new element to the collection
     *
     * @param request
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        if (Objects.isNull(request.getObject())){
            return new Response(ResponseStatus.ASK_OBJECT, "For command " + this.getName() + " needs argument");
        } else{
            collectionManager.add(request.getObject());
            return new Response(ResponseStatus.OK, "Element has been successfully added\n");
        }
    }
}

