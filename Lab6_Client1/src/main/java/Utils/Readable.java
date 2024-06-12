package Utils;

import Data.Color;
import Data.MovieGenre;
import Data.MpaaRating;

public interface Readable {
    String readName();
    Float readCoordinateX();
    Float readCoordinateY();
    Integer readOscarsCount();
    Long readBudget();
    MovieGenre readMovieGenre();
    MpaaRating readMpaaRating();
    String readOperatorsName();
    Float readWeight();
    Color readEyeColor();
}
