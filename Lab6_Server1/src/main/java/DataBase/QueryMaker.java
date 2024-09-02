package DataBase;

public class QueryMaker {

    String findUser = """
            SELECT * FROM User
            WHERE login = ?
            """;

    //password

    String addUser = """
            INSERT INTO User(login, password)
            VALUES (?, ?)
            """;

    String addMovie = """
            INSERT INTO movies(name, coordinate_x, coordinate_y, creation_date, oscars_count, budget, genre, mpaaRating, operators_name, operators_weight, operators_eye_color, author)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
             """;
    String addToDBMovie = """
            INSERT INTO movies(name, coordinate_x, coordinate_y, creation_date, oscars_count, budget, genre, mpaaRating, operators_name, operators_weight, operators_eye_color, author)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
             """;
    String clear = """
            DELETE FROM movies
            WHERE (user_login = ?)
            RETURNING id;
            """;
    String update = """
            Update movies SET (name, coordinate_x, coordinate_y, creation_date, oscars_count, budget, genre, mpaaRating, operators_name, operators_weight, operators_eye_color)
            = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            WHERE (user_login = ?) AND (id = ?)
            RETURNING id;
            """;
    String selectAll = """
            select * from movies
            """;
    String deleteAll = "DELETE from movies;";

}
