package Commands;

import Collection.CollectionManager;
import Collection.CollectionUtil;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;
import Errors.NoSuchIDException;

import java.io.Serial;
import java.io.Serializable;

public class RemoveById extends Command implements Serializable {
    private final CollectionManager collectionManager;

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", "remove_by_id {id}: remove element from collection by its id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            int ID = Integer.parseInt(request.getArgs().trim());
            if (!CollectionUtil.checkExist(ID)) throw new NoSuchIDException();
            collectionManager.removeById(ID);
            return new Response(ResponseStatus.OK,"Element has been successfully removes\n");
        } catch (NoSuchIDException err) {
            return new Response(ResponseStatus.ERROR,"There is no element with this id\n");
        } catch (NumberFormatException exception) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,"id must be integer\n");
        }
    }
}
