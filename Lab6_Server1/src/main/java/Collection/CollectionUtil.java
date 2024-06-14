package Collection;

import Data.Movie;

public class CollectionUtil {
    Validator validator = new Validator();

    public static String display(Movie movie) {
        return ("Element's ID  – " + movie.getId() +
                "\nMovie's name – " + movie.getName() +
                "\nCoordinate X – " + movie.getCoordinates().getX() +
                "\nCoordinate Y – " + movie.getCoordinates().getY() +
                "\nData of initialization – " + movie.getCreationDate() +
                "\nOscars count – " + movie.getOscarsCount() +
                "\nBudget – " + movie.getBudget() +
                "\nMovie genre – " + movie.getMovieGenre() +
                "\nMpaaRating – " + movie.getMpaaRating() +
                "\nOperator's name – " + movie.getOperator().getName() +
                "\nOperator's weight – " + movie.getOperator().getWeight() +
                "\nOperator's eye color - " + movie.getOperator().getEyeColor()+
                "\n-----------------------------------------------------------------------------\n"
        );
    }

}
