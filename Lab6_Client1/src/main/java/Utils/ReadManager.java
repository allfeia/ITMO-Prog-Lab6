package Utils;

import Console.*;
import Data.Color;
import Data.MovieGenre;
import Data.MpaaRating;
import Errors.FileModeException;

import java.util.Arrays;

public class ReadManager implements Readable{
    private final ReaderWriter console;
    private final UserInput scanner;
    public ReadManager(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new Console()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    @Override
    public String readName(){
        String name;
        while (true) {
            console.write("Enter name of movie:");
            name = console.readLine().trim();
            if (name.equals("") || !name.matches("^[a-zA-Z-А-Яа-я]*$")){
                console.printError("Name can't be empty line or not letters");
                if (Console.isFileMode()) throw new FileModeException();
            } else {
                return name;
            }
        }
    }

    @Override
    public Float readCoordinateX(){
        while (true){
            console.write("Enter coordinate X: ");
            try{
                return console.readFloat();
            }catch (NumberFormatException e){
                console.printError("The number is entered incorrectly. Enter it again: ");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }

    @Override
    public Float readCoordinateY(){
        while (true){
            console.write("Enter coordinate Y: ");
            try{
                float coordinateY = console.readFloat();
                if(coordinateY <= 347) {
                    return coordinateY;
                }else{
                    console.printError("Coordinate Y must be less then 347: ");
                }
            }catch (NumberFormatException e){
                console.printError("The number is entered incorrectly. Enter it again: ");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
    @Override
    public Integer readOscarsCount(){
        while (true){
            console.write("Enter oscars count: ");
            try{
                int oscarsCount = console.readInt();
                if(oscarsCount > 0){
                    return oscarsCount;
                }else{
                    console.printError("Count of oscars must be more then 0. Enter it again: ");
                }
            }catch (NumberFormatException e){
                console.printError("The number is entered incorrectly. Enter it again: ");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
    @Override
    public Long readBudget(){
        while (true){
            console.write("Enter budget: ");
            try{
                long budget = console.readLong();
                if(budget > 0){
                    return budget;
                }else{
                    console.printError("Budget must be more then 0. Enter it again: ");
                }
            }catch (NumberFormatException e){
                console.printError("The number is entered incorrectly. Enter it again: ");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
    @Override
    public MovieGenre readMovieGenre(){
        console.write("Enter movie genre: " + Arrays.toString(MovieGenre.values()));
        while (true){
            try{
                return MovieGenre.valueOf(console.getValidatedValue("\nEnter movie genre: ").toUpperCase());
            }catch (IllegalArgumentException e){
                console.printError("Genre is entered incorrectly. Enter it again: ");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
    @Override
    public MpaaRating readMpaaRating(){
        console.write("Enter mpaaRating: " + Arrays.toString(MpaaRating.values()));
        while (true){
            try{
                return MpaaRating.valueOf(console.getValidatedValue("\nEnter mpaaRating: ").toUpperCase());
            }catch (IllegalArgumentException e){
                console.printError("MpaaRatig is entered incorrectly. Enter it again: ");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
    @Override
    public String readOperatorsName(){
        String name;
        while (true) {
            console.write("Enter operator's name:");
            name = console.readLine();
            if (name.isEmpty() || name.isBlank()) {
                console.printError("Name can't be empty");
                if (Console.isFileMode()) throw new FileModeException();
            } else {
                return name;
            }
        }
    }

    @Override
    public Float readWeight(){
        while (true){
            console.write("Enter operator's weight: ");
            try{
                float bands = console.readFloat();
                if (bands > 0){
                    return bands;
                }else{
                    System.out.println("Operator's weight must be more then 0. Enter it again: ");
                }
            }catch (NumberFormatException e){
                console.printError("The number is entered incorrectly. Enter it again: ");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
    @Override
    public Color readEyeColor(){
        console.write("Enter operator's eye color: " + Arrays.toString(Color.values()));
        while (true){
            try{
                return Color.valueOf(console.getValidatedValue("\nEnter operator's eye color: ").toUpperCase());
            }catch (IllegalArgumentException e){
                console.printError("Operator's eye color is entered incorrectly. Enter it again: ");
                if (Console.isFileMode()) throw new FileModeException();
            }
        }
    }
}
