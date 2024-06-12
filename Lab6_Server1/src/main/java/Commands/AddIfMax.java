package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;

import java.io.Serializable;
import java.util.Objects;

public class AddIfMax extends Command implements Serializable {
    private final CollectionManager collectionManager;
    public AddIfMax(CollectionManager collectionManager){
        super("add_if_max", "add_if_max {element}: add new movie if oscars count in it more then in any in collection");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Request request) throws IllegalArgumentException{
        if (!request.getArgs().isBlank()) throw new IllegalArgumentException();
        if (Objects.isNull(request.getObject())){
            return new Response(ResponseStatus.ASK_OBJECT, "For command " + this.getName() + " needs argument");
        }else{
            return new Response(ResponseStatus.OK, collectionManager.addIfMax(request.getObject()));
        }
    }
}
