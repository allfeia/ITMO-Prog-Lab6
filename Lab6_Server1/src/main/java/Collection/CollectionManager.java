package Collection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import Data.Movie;
import Data.MovieGenre;
import Errors.IncorrectCollectionException;

public class CollectionManager implements Serializable {

    private static HashSet<Movie> movies = new HashSet<>();
    private static Deque<String> commandHistory = new LinkedList<>();
    CollectionUtil collectionUtil = new CollectionUtil();

    private static LocalDateTime localDateTime = LocalDateTime.now();

    private String filename;
    public CollectionManager() {
    }
    public CollectionManager(HashSet<Movie> movies, String filename) {
        this.movies = movies;
        this.filename = filename;
    }

    public static HashSet<Movie> getMovies() {
        return movies;
    }

    public void setCollection(HashSet<Movie> movie) {
        this.movies = movie;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public static HashSet<Movie> getMovie() {
        return movies;
    }

    public static String info() {
        commandHistory.add("\ninfo\n");
        return "Type of collection: " + movies.getClass().getSimpleName() + "\nDate of initialization: " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\nCount of elements: " + movies.size();

    }

    public static String show() {
        commandHistory.add("\nshow\n");
        if (movies.isEmpty()) {
            System.out.println("В коллекции нет объектов, доступных для просмотра");
        }
        StringBuilder information = new StringBuilder();
        for (Movie movie : movies) {
            information.append(CollectionUtil.display(movie));
        }
        return information.toString() + "\n";
    }
    public static void add(Movie movie) {
        commandHistory.add("\nadd\n");
        movies.add(movie);
    }
    public static String updateId(Movie newMovie, Integer ID) {
        commandHistory.add("\nupdate_id\n");
        boolean flag = movies.stream()
                .filter(movie1 -> movie1.getId() == newMovie.getId())
                .findFirst()
                .map(movie1 -> {
                    movie1.setName(newMovie.getName());
                    movie1.setCoordinates(newMovie.getCoordinates());
                    movie1.setOscarsCount(newMovie.getOscarsCount());
                    movie1.setBudget(newMovie.getBudget());
                    movie1.setMovieGenre(newMovie.getMovieGenre());
                    movie1.setMpaaRating(newMovie.getMpaaRating());
                    movie1.setOperator(newMovie.getOperator());
                    return movie1;
                })
                .isPresent();
        return !flag ? "The item with this id is not in the collection." : "Element has been successfully updated.";
    }

    public void removeById(Integer ID) {
        commandHistory.add("\nremove_by_id\n");
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            {
                if (movie.getId().equals(ID)) {
                    iterator.remove();
                    System.out.println("Element has been removed from the collection");
                } else if (!iterator.hasNext()) {
                    System.out.println("There is no element with this ID");
                }
            }
        }
    }

    public static void clear() {
        commandHistory.add("\nclear\n");
        movies.clear();
    }
    public static String addIfMax(Movie newMovie) {
        commandHistory.add("\nadd_if_max\n");
        if (!movies.isEmpty()) {
            int maxOscarsCount = movies.stream()
                    .mapToInt(Movie::getOscarsCount)
                    .max()
                    .getAsInt();
            if (newMovie.getOscarsCount() > maxOscarsCount) {
                add(newMovie);
                return "Element has been successfully added to the collection. ";
            } else {
                return "The item has not been added to the collection (not the largest). ";
            }
        }else {
            add(newMovie);
            return "aaaaa";
        }
    }

    public static String addIfMin(Movie newMovie) {
        commandHistory.add("\nadd_if_min\n");
        if (!movies.isEmpty()) {
            int minOscarsCount = movies.stream()
                    .mapToInt(Movie::getOscarsCount)
                    .min()
                    .getAsInt();
            if (newMovie.getOscarsCount() < minOscarsCount) {
                add(newMovie);
                return "Element has been successfully added to the collection. ";
            } else {
                return "The item has not been added to the collection (not the largest). ";
            }
        }else {
            add(newMovie);
            return "aaaaa";
        }
    }
    public static String sumOfOscarsCount() {
        commandHistory.add("\nsum_of_oscars_count\n");
        int sumOfOscarsCount = movies.stream()
                .mapToInt(Movie::getOscarsCount)
                .sum();
        return "The sum of the field values oscarsCount: " + sumOfOscarsCount;
    }
    public ArrayList<MovieGenre> printUniqueMovieGenre() {
        commandHistory.add("\nprint_unique_genre\n");
        ArrayList<MovieGenre> uniq = new ArrayList<>();
        for (Movie movie : movies) {
            MovieGenre genre = movie.getMovieGenre();
            if (Collections.frequency(uniq, genre) == 0) {
                uniq.add(genre);
            }
        }
        return uniq;
    }
    public void groupCountingByName() {
        commandHistory.add("\ngroup_counting_by_name\n");
        Map<String, Long> nameCountMap = movies.stream()
                .collect(Collectors.groupingBy(Movie::getName, Collectors.counting()));

        System.out.println("Grouped counting by name:");
        nameCountMap.forEach((name, count) ->
                System.out.println(name + ": " + count)
        );
    }
    public void addToHistory(String command) {
        commandHistory.add(command);
        if (commandHistory.size() > 7) {
            commandHistory.removeFirst();
        }
    }
    public String history() {
        commandHistory.add("\nhistory\n");
        StringBuilder history = new StringBuilder();
        history.append("History of commands: ");
        commandHistory.stream()
                .limit(7)
                .forEach(history::append);
        return history.toString();
    }
    public boolean checkCollection() {
        for (Movie movie : movies) {
            if (!collectionUtil.checkIfCorrect(movie)) {
                throw new IncorrectCollectionException("The source data in the collection is incorrect, correct the file and try again");
            }
        }
        return true;
    }

}
