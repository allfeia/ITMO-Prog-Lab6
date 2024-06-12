package Commands;

import ConnectionUtils.Request;
import ConnectionUtils.Response;
import Errors.CommandRuntimeException;
import Errors.ExitObligedException;
import Errors.IllegalArgumentsException;

public interface Executable {
    Response execute(Request request) throws Exception;
}
