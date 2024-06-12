package Commands;

import Collection.CollectionManager;
import Collection.CollectionUtil;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;
import Errors.NoSuchIDException;

import java.io.Serializable;
import java.util.Objects;

public class UpdateId extends Command implements Serializable {
    private final CollectionManager collectionManager;

    public UpdateId(CollectionManager collectionManager) {
        super("update", "update {element}: update element in collection by its id");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            int ID = Integer.parseInt(request.getArgs().trim());
            if (!CollectionUtil.checkExist(ID)) throw new NoSuchIDException();
            if (Objects.isNull(request.getObject())){
                return new Response(ResponseStatus.ASK_OBJECT, "For command " + this.getName() + " needs argument");
            }
            collectionManager.updateId(request.getObject(), ID);
            return new Response(ResponseStatus.OK, "Elements has been successfully updated\n");
        } catch (NoSuchIDException err) {
            return new Response(ResponseStatus.ERROR,"There is no element with this id");
        } catch (NumberFormatException exception) {
            return new Response(ResponseStatus.ERROR,"id must be integer");
        }
    }
}
