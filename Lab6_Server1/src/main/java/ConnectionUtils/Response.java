package ConnectionUtils;

import Data.Movie;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

/**
 * Класс Response представляет собой ответ сервера клиенту.
 * Он включает в себя статус ответа, сообщение и коллекцию объектов типа Movie.
 * Данный класс имплементирует интерфейс Serializable, что позволяет сериализовать его объекты.
 */

public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 25L;

    private final String result;

    public Response(String result){
        this.result = result;
    }

    public String getResult(){
        return result;
    }
}
