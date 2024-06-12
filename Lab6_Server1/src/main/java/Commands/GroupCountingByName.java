package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.CommandRuntimeException;
import Errors.ExitObligedException;
import Errors.IllegalArgumentsException;

import java.io.Serializable;

public class GroupCountingByName extends Command implements Serializable {
    private CollectionManager collectionManager;

    public GroupCountingByName(CollectionManager collectionManager) {
        super("group_counting_by_name", "group_counting_by_name: get grouped movies by name and their count");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) throws CommandRuntimeException, ExitObligedException, IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        collectionManager.groupCountingByName();
        return new Response(ResponseStatus.OK,"Elements has been grouped by name");
    }
}
