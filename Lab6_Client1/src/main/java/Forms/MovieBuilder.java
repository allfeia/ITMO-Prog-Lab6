package Forms;

import Data.*;

import java.time.LocalDate;

/**
 * Класс строитель для создания экземпляров класса Movie
 *
 */

public class MovieBuilder {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private int oscarsCount;
    private long budget;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person operator;

//    /**
//     * Вспомогательная константа для автоматической генерации значения id
//     */
//
//    private static Integer nextId = Math.toIntExact(1L);
//
//    /*
//      Блок инициализации задающий значения полей creationDate и id
//     */
//
//    {
//        this.creationDate = LocalDate.now();
//        this.id = nextId;
//        nextId++;
//    }

//    public void withId(Integer id){
//        if (id < 0) throw new IllegalArgumentException("id must be positive integer");
//        this.id = id;
//    }

    public void withId(Integer id){
        id = null;
    }

    public void withName(String name){
        if (name.isBlank()) throw new NullPointerException("call movie!!!");
        this.name = name;
    }

    public void withCoordinates(Coordinates coordinates){
        if (coordinates == null) throw new NullPointerException("we need your coordinates)))))");
        this.coordinates = coordinates;
    }

    public void withCreationDate(LocalDate date){
        this.creationDate = LocalDate.now();
    }

    public void withOscarsCount(int oscarsCount){
        if (oscarsCount <= 0) throw new NullPointerException("you don't need bad movie in your collection");
        this.oscarsCount = oscarsCount;
    }

    public void withBudget(long budget){
        if (budget < 0) throw new NullPointerException("FREE MOVIE???????");
        this.budget = budget;
    }

    public void withMovieGenre(MovieGenre genre){
        if (genre == null) throw new IllegalArgumentException("I don't know this genre..");
        this.genre = genre;
    }

    public void withMpaaRating(MpaaRating mpaaRating){
        if (mpaaRating == null) throw new IllegalArgumentException("0+ ?");
        this.mpaaRating = mpaaRating;
    }

    public void withPerson(Person operator){
        if (operator == null) throw new NullPointerException("how u can watched this movie if it hasn't operator?");
        this.operator = operator;
    }

    public boolean isReadyToBuild(){
        return !(this.name == null
                || this.coordinates == null
                || this.oscarsCount <= 0
                || this.budget < 0
                || this.genre == null
                || this.mpaaRating == null
                || this.operator == null);
    }
    public Movie build(){
        return new Movie(name, coordinates, oscarsCount, budget, genre, mpaaRating, operator);
    }



}
