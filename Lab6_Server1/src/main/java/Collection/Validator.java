package Collection;

import Data.Color;
import Data.Movie;
import Data.MovieGenre;
import Data.MpaaRating;

public class Validator {
    public boolean checkName(String name){
        if (name.equals("") || !name.matches("^[a-zA-Z-А-Яа-я]*$")) {
            return false;
        }
        return true;
    }

    public boolean checkCoordinateX(Float X) {
        if (X == null) {
            return false;
        }
        return true;
    }

    public boolean checkCoordinateY(Float Y){
        if (Y > 347 || Y == null){
            return false;
        }
        return true;
    }

    public boolean checkOscarsCount(Integer oscarsCount) {
        if (oscarsCount < 0) {
            return false;
        }
        return true;
    }

    public boolean checkBudget(long budget){
        if (budget < 0){
            return false;
        }
        return true;
    }
    public boolean checkMovieGenre(MovieGenre genre) {
        for (MovieGenre genre1 : MovieGenre.values()) {
            if (!genre1.name().equals(genre)){
                return false;
            }
        }
        return true;
    }

    public boolean checkMpaaRating(MpaaRating mpaaRating){
        for (MpaaRating mpaaRating1 : MpaaRating.values()) {
            if (!mpaaRating1.name().equals(mpaaRating)){
                return false;
            }
        }
        return true;
    }
    public boolean checkOperatorsName(String name){
        if (name.equals("") || !name.matches("^[a-zA-Z-А-Яа-я]*$")) {
            return false;
        }
        return true;
    }
    public boolean checkWeight(Float weight) {
        if (weight < 0) {
            return false;
        }
        return true;
    }
    public boolean checkEyeColor(Color eyeColor){
        for (Color eyeColor1 : Color.values()) {
            if (!eyeColor1.name().equals(eyeColor)){
                return false;
            }
        }
        return true;
    }
    public Movie getValidated(Movie movie){
        if (checkName(movie.getName()) && checkCoordinateX(movie.getCoordinates().getX()) && checkCoordinateY(movie.getCoordinates().getY()) && checkOscarsCount(movie.getOscarsCount()) &&checkBudget(movie.getBudget()) && checkMovieGenre(movie.getMovieGenre()) && checkMpaaRating(movie.getMpaaRating()) && checkOperatorsName(movie.getOperator().getName()) && checkWeight(movie.getOperator().getWeight()) && checkEyeColor(movie.getOperator().getEyeColor())) {
        }
        return movie;
    }
}
