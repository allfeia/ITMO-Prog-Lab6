package Commands;

import Collection.CollectionManager;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Data.Movie;

import java.io.Serial;
import java.io.Serializable;

public class ExecuteScript implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    @Override
    public Response execute(Object args, Movie movie, CollectionManager collectionManager) {
        if (args == null)
            throw new NullPointerException("enter argument - filename");
        String filename = args.toString();
        return new Response("start to read " + filename);
    }

    @Override
    public String description() {
        return "execute_script {file_name}: execute script with commands";
    }

    @Override
    public String getName(){
        return "execute_script";
    }

}
