package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.CommandRuntimeException;
import Errors.ExitObligedException;
import Errors.IllegalArgumentsException;

import java.io.Serializable;

public class Clear extends Command implements Serializable {
    private CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "clear: clear collection");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) throws CommandRuntimeException, ExitObligedException, IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        collectionManager.clear();
        return new Response(ResponseStatus.OK,"Collection has been successfully cleared\n");
    }
}
