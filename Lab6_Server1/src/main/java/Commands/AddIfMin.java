package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;

import java.io.Serializable;
import java.util.Objects;

public class AddIfMin extends Command implements Serializable {
    private final CollectionManager collectionManager;
    public AddIfMin(CollectionManager collectionManager){
        super("add_if_min", "add_if_min {element}: add new movie if oscars count in it less then in any in collection");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Request request) throws IllegalArgumentException{
        if (!request.getArgs().isBlank()) throw new IllegalArgumentException();
        if (Objects.isNull(request.getObject())){
            return new Response(ResponseStatus.ASK_OBJECT, "For command " + this.getName() + " needs argument");
        }else{
            collectionManager.addIfMin(request.getObject());
            return new Response(ResponseStatus.OK, "Movie has been successfully added");
        }
    }
}
