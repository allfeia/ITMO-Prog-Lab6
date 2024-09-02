package DataBase;

import Data.Movie;

import java.sql.Connection;
import java.util.HashSet;

public interface Parser {
    void save();
    HashSet<Movie> load();

}
