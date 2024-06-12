package Data;

public class MovieCreator {
    public static Movie createMovie(String[] fields){
        try{
            String name = fields[0];
            float x = Float.parseFloat(fields[1]);
            float y = Float.parseFloat(fields[2]);
            int oscarsCount = Integer.parseInt(fields[3]);
            long budget = Long.parseLong(fields[4]);
            MovieGenre movieGenre = MovieGenre.valueOf(fields[5].toUpperCase());
            MpaaRating mpaaRating = MpaaRating.valueOf(fields[6].toUpperCase());
            String operatorsName = fields[7];
            float weight = Float.parseFloat(fields[8]);
            Color eyeColor = Color.valueOf(fields[9].toUpperCase());
            return new Movie(name, new Coordinates(x, y), oscarsCount, budget, movieGenre, mpaaRating, new Person(operatorsName, weight, eyeColor));
        } catch (Exception e){
            return null;
        }
    }
}
