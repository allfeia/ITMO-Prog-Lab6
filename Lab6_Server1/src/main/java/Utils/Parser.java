package Utils;

import Data.IdGenerator;
import Collection.Validator;
import Data.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashSet;

public class Parser {
    private static String fileName = "MovieCollection.csv";

    public Parser(String fileName) {
        Parser.fileName = fileName;
    }
    public static String getFileName(){
        return fileName;
    }
    public Parser(){}

    public static void saveToCSV() throws Exception {
        HashSet<Movie> movies = new HashSet<>();
        FileWriter writer = null;
        try {
            Path filePath = Paths.get(getFileName());
            if (!Files.isWritable(filePath)){
                ServerLogger.getLogger().warning("Insufficient permissions to write to the file");
            }
            writer = new FileWriter(getFileName());
            writer.write("id;name;coordinates_x;coordinates_y;creationDate;oscarsCount;budget;genre;mpaaRating;operator_name;operator_weight;operator_eyeColor\n");

            for (Movie movie : movies) {
                writer.write(movie.getId() + ";");
                writer.write(movie.getName() + ";");
                writer.write(movie.getCoordinates().getX() + ";");
                writer.write(movie.getCoordinates().getY() + ";");
                writer.write(movie.getCreationDate() + ";");
                writer.write(movie.getOscarsCount() + ";");
                writer.write(movie.getBudget() + ";");
                writer.write(movie.getMovieGenre() + ";");
                writer.write(movie.getMpaaRating() + ";");
                writer.write(movie.getOperator().getName() + ";");
                writer.write(movie.getOperator().getWeight() + ";");
                writer.write(movie.getOperator().getEyeColor() + "\n");
            }
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Error writing to the file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    ServerLogger.getLogger().warning("Error closing the file: " + e.getMessage());
                }
            }
        }
    }

    public static HashSet<Movie> loadFromCSV() throws Exception {
        HashSet<Movie> movies = new HashSet<>();
        Validator validator = new Validator();
        IdGenerator idGenerator = new IdGenerator();
        try {
            File file = new File(getFileName());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            if (reader.readLine() == null) {
                ServerLogger.getLogger().info("CSV file is empty.");
            }
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(";");
                if (columns.length < 12) {
                    ServerLogger.getLogger().warning("Incorrect CSV format. Skipping line.");
                }

                Movie movie = new Movie(Integer.parseInt(columns[0]),
                        columns[1],
                        new Coordinates(Float.parseFloat(columns[2]), Float.parseFloat(columns[3])),
                        LocalDate.parse(columns[4]),
                        Integer.parseInt(columns[5]),
                        Long.parseLong(columns[6]),
                        MovieGenre.valueOf(columns[7]),
                        MpaaRating.valueOf(columns[8]),
                        new Person(columns[9], Float.parseFloat(columns[10]), Color.valueOf(columns[11])));

                Movie validatedMovie = validator.getValidated(movie);
                if (validatedMovie != null && !idGenerator.getIdList().contains(validatedMovie.getId())) {
                    movies.add(validatedMovie);
                    idGenerator.addId(validatedMovie);
                } else {
                    ServerLogger.getLogger().warning("Skipping invalid element.");
                }
            }
            return movies;
        } catch (IOException e) {
            ServerLogger.getLogger().warning("An error occurred while loading data from CSV file: " + e.getMessage());
            return movies;
        }
    }
}
