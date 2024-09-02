package Data;

import java.io.Serializable;
import java.time.LocalDate;

public class Movie implements Comparable<Movie>, Serializable {
    private static final long serialVersionUID = 315L;
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private int oscarsCount;
    private long budget;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person operator;
    //IdGenerator idGenerator = new IdGenerator();

    public Movie(String name, Coordinates coordinates, int oscarsCount, long budget, MovieGenre genre, MpaaRating mpaaRating, Person operator) {
        if (name == null || name.isBlank() || coordinates == null || oscarsCount <= 0 || genre == null || mpaaRating == null || operator == null) {
            throw new IllegalArgumentException("The fields can't be null or empty");
        } else {
            this.id = null;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = LocalDate.now();
            this.oscarsCount = oscarsCount;
            this.budget = budget;
            this.genre = genre;
            this.mpaaRating = mpaaRating;
            this.operator = operator;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = java.time.LocalDate.now();
    }
    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(Integer oscarsCount) {
        this.oscarsCount = oscarsCount;
    }
    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }
    public MovieGenre getMovieGenre() {
        return genre;
    }

    public void setMovieGenre(MovieGenre genre) {
        this.genre = genre;
    }
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getOperator() {
        return operator;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    @Override
    public int compareTo(Movie movie){
        if (oscarsCount > movie.getOscarsCount()){
            return 1;
        }else if (oscarsCount < movie.oscarsCount){
            return -1;
        }else{
            return 0;
        }
    }
    public boolean equals(Movie movie){
        return (this.id == movie.getId()) || (this.name.equals(movie.getName()) && this.coordinates.equals(movie.getCoordinates()) && this.oscarsCount == movie.getOscarsCount() && this.budget == movie.getBudget() && this.genre.equals(movie.getMovieGenre()) && this.mpaaRating.equals(movie.getMpaaRating()) && this.operator.equals(movie.getOperator()));
    }

    @Override
    public String toString() {
        return "Movie\n" +
                "id: " + id + "\n" +
                "name: " + name + "\n" +
                "coordinates: " + coordinates + "\n" +
                "creationDate: " + creationDate + "\n" +
                "oscarsCount: " + oscarsCount + "\n" +
                "budget: " + budget + "\n" +
                "genre: " + genre + "\n" +
                "mpaaRating: " + mpaaRating + "\n" +
                "operator: " + operator;
    }
    public boolean validate(){
        return this.id >= 0 &&
                this.name != null &&
                this.coordinates != null &&
                this.creationDate != null &&
                this.oscarsCount >= 0 &&
                this.genre != null &&
                this.budget >= 0 &&
                this.mpaaRating != null &&
                this.operator != null;

    }
}
