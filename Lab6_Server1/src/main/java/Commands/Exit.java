package Commands;

import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;
import Utils.Parser;

import java.io.Serializable;

public class Exit extends Command implements Serializable {

    public Exit() {
        super("exit", "exit: end app with saving collection into file");
    }
    @Override
    public Response execute(Request request) throws Exception {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            Parser.saveToCSV();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response(ResponseStatus.EXIT);
    }
}
