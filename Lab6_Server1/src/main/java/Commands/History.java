package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.CommandRuntimeException;
import Errors.ExitObligedException;
import Errors.IllegalArgumentsException;

import java.io.Serializable;

public class History extends Command implements Serializable {
    private CollectionManager collectionManager;

    public History(CollectionManager collectionManager) {
        super("history", "history: get last 7 commands");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) throws CommandRuntimeException, ExitObligedException, IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(ResponseStatus.OK,collectionManager.history());
    }
}
