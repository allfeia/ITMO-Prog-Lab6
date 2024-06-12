package Collection;

import Data.Movie;

public class CollectionUtil {
    static Validator validator = new Validator();
    public static boolean checkExist(Integer ID) {
        for (Movie movie:CollectionManager.getMovies()) {
            if (movie.getId().equals(ID))
                return true;
        }
        return false;
    }
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
    public boolean checkIfCorrect(Movie movie){
        if (
                validator.checkName(movie.getName()) ||
                validator.checkCoordinateX(movie.getCoordinates().getX()) ||
                validator.checkCoordinateY(movie.getCoordinates().getY()) ||
                validator.checkOscarsCount(movie.getOscarsCount()) ||
                validator.checkBudget(movie.getBudget()) ||
                validator.checkMovieGenre(movie.getMovieGenre()) ||
                validator.checkMpaaRating(movie.getMpaaRating()) ||
                validator.checkOperatorsName(movie.getOperator().getName()) ||
                validator.checkWeight(movie.getOperator().getWeight()) ||
                validator.checkEyeColor(movie.getOperator().getEyeColor())

        ){
            return true;
        }
        return false;
    }

}
