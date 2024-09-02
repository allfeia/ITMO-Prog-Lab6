package DataBase;

import java.sql.*;

import Authorisation.PasswordManager;
import Authorisation.User;
import ConnectionUtils.Response;
import Data.Movie;
import Utils.ServerLogger;

public class DataBaseManager {
    private QueryMaker queryManager = new QueryMaker();
    private Connection connection;

    public static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "aliska", "Cringe" );
            if (connection != null) {
                ServerLogger.getLogger().info("Connection Established");
            } else {
                ServerLogger.getLogger().warning("Connection Failed");
            }
        } catch (Exception e){
            ServerLogger.getLogger().warning("" + e);
        }
        return connection;
    }


    public int addObject(Movie newMovie){
        try{
            Connection connection = connect();
            PreparedStatement add = connection.prepareStatement(queryManager.addMovie);
            //add.setInt(1, newMovie.getId());
            add.setString(1, newMovie.getName());
            add.setFloat(2, newMovie.getCoordinates().getX());
            add.setFloat(3, newMovie.getCoordinates().getY());
            add.setString(4, newMovie.getCreationDate().toString());
            add.setInt(5, newMovie.getOscarsCount());
            add.setLong(6, newMovie.getBudget());
            add.setString(7, newMovie.getMovieGenre().toString());
            add.setString(8, newMovie.getMpaaRating().toString());
            add.setString(9, newMovie.getOperator().getName());
            add.setFloat(10, newMovie.getOperator().getWeight());
            add.setString(11, newMovie.getOperator().getEyeColor().toString());
            add.setString(12, newMovie.getUser_login());
            ResultSet resultSet = add.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");

        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return -1;
    }
    public boolean updateObject(Movie newMovie, String user){
        try{
            Connection connection = connect();
            PreparedStatement update = connection.prepareStatement(queryManager.update);
            update.setString(1, newMovie.getName());
            update.setFloat(2, newMovie.getCoordinates().getX());
            update.setFloat(3, newMovie.getCoordinates().getY());
            update.setInt(4, newMovie.getOscarsCount());
            update.setLong(5, newMovie.getBudget());
            update.setString(6, newMovie.getMovieGenre().toString());
            update.setString(7, newMovie.getMpaaRating().toString());
            update.setString(8, newMovie.getOperator().getName());
            update.setFloat(9, newMovie.getOperator().getWeight());
            update.setString(10, newMovie.getOperator().getEyeColor().toString());
            update.setString(11, user);
            update.setInt(12, newMovie.getId());
            ResultSet resultSet = update.executeQuery();
            return (resultSet.next());
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return false;
    }


    //TODO: registration and all commands which work with collection




    public Response registration(User user){
        try {
            Connection connection = connect();
            PreparedStatement findUser = connection.prepareStatement(queryManager.findUser);
            findUser.setString(1, user.getLogin());
            ResultSet resultSet = findUser.executeQuery();
            if (!resultSet.next()){
                PasswordManager passwordManager = new PasswordManager();
                PreparedStatement addUser = connection.prepareStatement(queryManager.addUser);
                addUser.setString(1, user.getLogin());
                addUser.setString(2, user.getPassword());
                addUser.setString(3, passwordManager.hashPassword(user.getPassword()));
                addUser.execute();
                return new Response("Регистрация прошла успешно!");
            } else {
                return new Response("Пользователь с таким логином уже существует. Попробуй еще раз: ");
            }
        } catch (SQLException | NullPointerException e){
            return new Response("Ошибка подключения к базе данных. Попробуй еще раз: ");
        }
    }
    public Response authorisation(User user){
        try{
            Connection connection = connect();
            PreparedStatement findUser = connection.prepareStatement(queryManager.findUser);
            findUser.setString(1, user.getLogin());
            ResultSet resultSet = findUser.executeQuery();
            if (resultSet.next()){
                if (resultSet.getString("password").equals(user.getPassword())) return new Response("Вход выполнен успешно!");
                return new Response( "Введен неверный пароль. Попробуй еще раз: ");
            } else {
                return new Response("Пользователь с таким логином не найден. Попробуй еще раз: ");
            }
        } catch (SQLException | NullPointerException e){
            return new Response("Ошибка подключения к базе данных. Попробуй еще раз: ");
        }
    }
}


