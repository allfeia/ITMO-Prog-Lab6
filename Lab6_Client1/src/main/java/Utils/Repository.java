package Utils;

import Data.Movie;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Класс - хранилищe
 * для хранения коллекции, которой управляет приложение
 */

public class Repository {
    private final Collection<Movie> collection;

    public Repository(Collection<Movie> collection) {
        this.collection = collection;
    }

    public Repository() {
        this.collection = new HashSet<Movie>();
    }

    public Collection<Movie> collection() {
        return collection;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Repository) obj;
        return Objects.equals(this.collection, that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection);
    }

    @Override
    public String toString() {
        return "Storage[" +
                "collection=" + collection + ']';
    }
}
