package ConnectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import Data.Movie;

/**
 * Класс Response представляет собой ответ сервера клиенту.
 * Он включает в себя статус ответа, сообщение и коллекцию объектов типа Movie.
 * Данный класс имплементирует интерфейс Serializable, что позволяет сериализовать его объекты.
 */

public class Response implements Serializable {
    private ResponseStatus responseStatus;
    private String response = "";
    private Collection<Movie> collection;

    public Response(){
    }

    /**
     * Конструктор для создания ответа со статусом и сообщением.
     * @param responseStatus Статус ответа
     * @param response Сообщение ответа
     */

    public Response(ResponseStatus responseStatus, String response){
        this.responseStatus = responseStatus;
        this.response = response.trim() + "\n";
    }

    /**
     * Конструктор для создания ответа со статусом, сообщением и коллекцией объектов типа Movie.
     * @param status Статус ответа
     * @param response Сообщение ответа
     * @param collection Коллекция объектов типа Movie
     */

    public Response(ResponseStatus status, String response, Collection<Movie> collection) {
        this.responseStatus = status;
        this.response = response.trim();
        this.collection = collection.stream()
                .sorted(Comparator.comparing(Movie::getId))
                .toList();
    }

    public Response(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }
    public String getResponse() {
        return response;
    }
    public Collection<Movie> getCollection() {
        return collection;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response response1)) return false;
        return responseStatus == response1.responseStatus && Objects.equals(response, response1.response) && Objects.equals(collection, response1.collection);
    }
    @Override
    public int hashCode() {
        return Objects.hash(responseStatus, response, collection);
    }
    @Override
    public String toString(){
        return "Response[" + responseStatus +
                (response.isEmpty()
                        ? ""
                        :',' + response) +
                (collection == null
                        ? ']'
                        : ',' + collection.toString() + ']');
    }
}
