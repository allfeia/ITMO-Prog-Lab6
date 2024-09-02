package DataBase;

import Collection.CollectionManager;
import Data.*;
import Utils.ServerLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class DataBaseParser implements Parser{
    private QueryMaker queryManager = new QueryMaker();
    private final Connection connection = DataBaseManager.connect();

    @Override
    public void save() {
        try{
            PreparedStatement deleteAll = connection.prepareStatement(queryManager.deleteAll);
            deleteAll.execute();
            CopyOnWriteArraySet<Movie> movies = CollectionManager.getCollection();
            for (Movie movie : movies){
                PreparedStatement add = connection.prepareStatement(queryManager.addToDBMovie);
                add.setInt(1, movie.getId());
                add.setString(2, movie.getName());
                add.setFloat(3, movie.getCoordinates().getX());
                add.setFloat(4, movie.getCoordinates().getY());
                add.setString(5, movie.getCreationDate().toString());
                add.setInt(6, movie.getOscarsCount());
                add.setLong(7, movie.getBudget());
                add.setString(8, movie.getMovieGenre().toString());
                add.setString(9, movie.getMpaaRating().toString());
                add.setString(10, movie.getOperator().getName());
                add.setFloat(11, movie.getOperator().getWeight());
                add.setString(12, movie.getOperator().getEyeColor().toString());
                add.setString(9, movie.getUser_login());
                add.executeQuery();
            }
            ServerLogger.getLogger().info("Коллекция сохранена в базу данных.");
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении к базе данных. Колекция не сохранена.");
        }
    }

    @Override
    public HashSet<Movie> load() {
        HashSet<Movie> movies = new HashSet<>();
        try {
            PreparedStatement selectAll = connection.prepareStatement(queryManager.selectAll);
            ResultSet result = selectAll.executeQuery();
            while (result.next()) {
                try {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    float x = result.getFloat("coordinate_x");
                    float y = result.getFloat("coordinate_y");
                    LocalDate creationDate = LocalDate.parse(result.getString("creation_date"));
                    int oscarsCount = result.getInt("oscars_count");
                    long budget = result.getLong("budget");
                    MovieGenre movieGenre = result.getString("genre") != null ? MovieGenre.valueOf(result.getString("genre")) : null;
                    MpaaRating mpaaRating = result.getString("mpaarating") != null ? MpaaRating.valueOf(result.getString("mpaaRating")) : null;
                    String operatorsName = result.getString("operators_name");
                    int weight = result.getInt("operators_weight");
                    Color eyeColor = result.getString("operators_eye_color") != null ? Color.valueOf(result.getString("operators_eye_color")) : null;
                    String user_login = result.getString("author");

                    Movie movie = new Movie( name, new Coordinates(x, y), oscarsCount, budget, movieGenre, mpaaRating, new Person(operatorsName, weight, eyeColor), user_login);
                    movies.add(movie);
                } catch (Exception ex) {
                    ServerLogger.getLogger().warning("Ошибка при парсинге строки результата:" + ex.getMessage());
                }
            }
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении или чтении данных из базы данных. Создана пустая коллекция");
        }
        return movies;
    }
}
