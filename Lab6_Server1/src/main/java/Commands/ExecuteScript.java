package Commands;

import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Errors.IllegalArgumentsException;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ExecuteScript  extends Command implements Serializable {
    static boolean flag = false;

    public static boolean isFlag() {
        return flag;
    }
    public ExecuteScript() {
        super("execute_script", "execute_script {file_name}: execute script with commands");
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        flag = true;
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(ResponseStatus.EXECUTE_SCRIPT, request.getArgs());
    }

}
