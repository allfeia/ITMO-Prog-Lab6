package Collection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import Commands.Command;
import ConnectionUtils.Response;
import Data.Movie;
import Data.MovieGenre;
import Utils.Repository;

public class CollectionManager implements Serializable {

    private final Collection<Movie> movies;
    private final String filename;

    private static Deque<String> commandHistory = new LinkedList<>();
    CollectionUtil collectionUtil = new CollectionUtil();
    private static LocalDateTime localDateTime = LocalDateTime.now();



    public CollectionManager(Repository movies, String filename) {
        this.movies = movies.collection();
        this.filename = filename;
    }


    public Response info() {
        commandHistory.add("\ninfo\n");
        return new Response("Type of collection: " + movies.getClass().getSimpleName() + "\nDate of initialization: " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\nCount of elements: " + movies.size());

    }

    public Response help(Collection<Command> commands){
        StringBuilder builder = new StringBuilder("list of accessfull commands: ");
        commands.forEach(command -> builder.append(command.description()).append("\n"));
        return new Response(builder.toString());
    }

    public Response show() {
        commandHistory.add("\nshow\n");
        if (movies.isEmpty()) {
            System.out.println("Collection is empty");
        }
        StringBuilder information = new StringBuilder();
        for (Movie movie : movies) {
            information.append(CollectionUtil.display(movie));
        }
        return new Response(information.toString() + "\n");
    }

    public Response add(Movie movie) {
        commandHistory.add("\nadd\n");
        movies.add(movie);
        return new Response( "Element has been successfully added " + movie);
    }

    public Response updateId(Movie newMovie, Integer ID) {
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
        return new Response(!flag ? "The item with this id is not in the collection." : "Element has been successfully updated.");
    }

    public Response removeById(Integer ID) {
        commandHistory.add("\nremove_by_id\n");
        var success = movies.removeIf(movie -> movie.getId().equals(ID));
        if (success) return new Response("Element has been removed from the collection");
        else return new Response("There is no element with this ID");
    }

    public Response clear() {
        commandHistory.add("\nclear\n");
        movies.clear();
        return new Response("Collection has been cleared");
    }

    public Response addIfMax(Movie movie){
        if (movie.compareTo(Collections.max(movies)) > 0) {
            movies.add(movie);
            return new Response("Element has been succssesfully added: " + movie);
        } else return new Response("Element hasn't been added to collection (not the largest)");
    }


    public Response addIfMin(Movie movie){
        if (movie.compareTo(Collections.min(movies)) > 0) {
            movies.add(movie);
            return new Response("Element has been succssesfully added: " + movie);
        } else return new Response("Element hasn't been added to collection (not the largest)");
    }

    public Response sumOfOscarsCount() {
        commandHistory.add("\nsum_of_oscars_count\n");
        int sumOfOscarsCount = movies.stream()
                .mapToInt(Movie::getOscarsCount)
                .sum();
        return new Response("The sum of the field values oscarsCount: " + sumOfOscarsCount);
    }

    public Response printUniqueMovieGenre() {
        commandHistory.add("\nprint_unique_genre\n");
        ArrayList<MovieGenre> uniq = new ArrayList<>();
        for (Movie movie : movies) {
            MovieGenre genre = movie.getMovieGenre();
            if (Collections.frequency(uniq, genre) == 0) {
                uniq.add(genre);
            }
        }
        return new Response("" + uniq);
    }

    public Response groupCountingByName() {
        commandHistory.add("\ngroup_counting_by_name\n");
        Map<String, Long> nameCountMap = movies.stream()
                .collect(Collectors.groupingBy(Movie::getName, Collectors.counting()));

        System.out.println("Grouped counting by name:");
        nameCountMap.forEach((name, count) ->
                System.out.println(name + ": " + count)
        );
        return new Response("");
    }

    public void addToHistory(String command) {
        commandHistory.add(command);
        if (commandHistory.size() > 7) {
            commandHistory.removeFirst();
        }
    }
    public Response history() {
        commandHistory.add("\nhistory\n");
        StringBuilder history = new StringBuilder();
        history.append("History of commands: ");
        commandHistory.stream()
                .limit(7)
                .forEach(history::append);
        return new Response(history.toString());
    }

}

