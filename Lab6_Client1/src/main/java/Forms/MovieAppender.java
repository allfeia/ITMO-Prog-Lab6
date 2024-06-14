package Forms;

import Data.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс предоставляющий методы для чтения фильмов введенных пользователем и построения объектов из них
 */

public class MovieAppender {

    /**
     * Метод для считывания объекта с консоли построчно, обрабатывает ввод и делает билет
     * @return введенный пользователем билет
     * @throws IllegalStateException если билет не получилось сделать
     */

    public static Movie appendMovie() {
        MovieBuilder builder = new MovieBuilder();
        Scanner in = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Enter name of movie:");
                String input = in.nextLine();
                builder.withName(input);
                break;
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("you make me upset.. bye");
            }
        }
        while (true) {
            try {
                System.out.println("Enter coordinates throw the space");
                String[] input = in.nextLine().split(" ");
                float x = Float.parseFloat(input[0]);
                float y = Float.parseFloat(input[1]);
                try {
                    if (input.length > 2) throw new IllegalArgumentException("Too many arguments");
                    builder.withCoordinates(new Coordinates(x, y));
                    break;
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NoSuchElementException e) {
                System.out.println("You make me upset.. bye");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("It doesn't fit to coordinates");
            }
        }


        while (true) {
            try {
                System.out.println("Enter budget:");
                String input = in.nextLine();
                Long budget = Long.parseLong(input);
                try {
                    builder.withBudget(budget);
                    break;
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("this number doesn't fit");
            } catch (NoSuchElementException e) {
                System.out.println("you make me upset.. bye");
                System.exit(0);
            }
        }
        while (true) {
            try {
                System.out.println("Enter oscars count:");
                String input = in.nextLine();
                Integer oscarsCount = Integer.parseInt(input);
                try {
                    builder.withOscarsCount(oscarsCount);
                    break;
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("this number doesn't fit");
            } catch (NoSuchElementException e) {
                System.out.println("you make me upset.. bye");
                System.exit(0);
            }
        }
        while (true) {
            try {
                System.out.println("Enter movie genre:");
                System.out.println(Arrays.toString(MovieGenre.values()));
                String input = in.nextLine().toUpperCase();
                var genre = MovieGenre.valueOf(input);
                builder.withMovieGenre(genre);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("I don't know this genre");
            } catch (NoSuchElementException e) {
                System.out.println("you make me upset.. bye");
                System.exit(0);
            }
        }
        while (true) {
            try {
                System.out.println("Enter mpaaRating:");
                System.out.println(Arrays.toString(MpaaRating.values()));
                String input = in.nextLine().toUpperCase();
                var mpaaRating = MpaaRating.valueOf(input);
                builder.withMpaaRating(mpaaRating);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("0+ ?");
            } catch (NoSuchElementException e) {
                System.out.println("you make me upset.. bye");
                System.exit(0);
            }
        }

        var operator = true;
        if (operator) {
            PersonBuilder personBuilder = new PersonBuilder();

            while (true) {
                try {
                    System.out.println("Enter operator's name:");
                    String input = in.nextLine();
                    personBuilder.withOperatorsName(input);
                    break;
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                } catch (NoSuchElementException e) {
                    System.out.println("you make me upset.. bye");
                }
            }
            while (true) {
                try {
                    System.out.println("Enter operator's weight:");
                    String input = in.nextLine();
                    Float weight = Float.parseFloat(input);
                    try {
                        personBuilder.withWeight(weight);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println("Is operator a ghost?");
                } catch (NoSuchElementException e) {
                    System.out.println("you make me upset.. bye");
                    System.exit(0);
                }
            }
            while (true) {
                try {
                    System.out.println("Enter operator's eye color:");
                    System.out.println(Arrays.toString(Color.values()));
                    String input = in.nextLine().toUpperCase();
                    var eyeColor = Color.valueOf(input);
                    personBuilder.withEyeColor(eyeColor);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Is he blind?");
                } catch (NoSuchElementException e) {
                    System.out.println("you make me upset.. bye");
                    System.exit(0);
                }
            }
            builder.withPerson(personBuilder.build());
        }

        if (builder.isReadyToBuild()) {
            return builder.build();
        } else throw new IllegalArgumentException("Something went wrong");
    }

    /**
     * Аналогичный метод читает объект из файла, в файле объект должен быть в фигурных скобках с полями через запятую
     * @param args строка из файла описывающая билет
     * @return введенный пользователем билет
     * @throws IllegalStateException если билет не получилось сделать
     * @throws IllegalArgumentException если в файле некорректные значения полей билета
     */


    public static Movie appendMovie(String args){
        try{
            MovieBuilder builder = new MovieBuilder();
            PersonBuilder personBuilder = new PersonBuilder();
            ArrayList<String> values = new ArrayList<>(List.of(args.substring(1)
                    .replace("}", "").split(", ")));
            values.addAll(List.of("", "", "", ""));

            builder.withName(values.get(0).substring(1).replace("'", ""));
            builder.withCreationDate(LocalDate.parse(values.get(1)));
            builder.withCoordinates(new Coordinates(Float.parseFloat(values.get(2)), Float.parseFloat(values.get(3))));
            builder.withOscarsCount(Integer.parseInt(values.get(4)));
            builder.withBudget(Long.parseLong(values.get(5)));
            builder.withMovieGenre(MovieGenre.valueOf(values.get(6)));
            builder.withMpaaRating(MpaaRating.valueOf(values.get(7)));
            personBuilder.withOperatorsName(values.get(8).substring(1).replace("'", ""));
            personBuilder.withWeight(Float.parseFloat(values.get(9)));
            personBuilder.withEyeColor(Color.valueOf(values.get(10)));

            if (builder.isReadyToBuild()) return builder.build();
            else throw new IllegalArgumentException("something wrong with arguments in movie");
        }catch (Exception e){
            throw new IllegalStateException("something went wrong with movie's description" + e.getMessage());
        }
    }


}
