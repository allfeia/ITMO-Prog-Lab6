package Forms;

import Data.*;
import Console.*;
import Utils.ExecuteFileManager;
import Utils.ReadManager;

import java.time.LocalDate;

public class MovieForm extends Form<Movie>{
    private final ReaderWriter console;
    private final UserInput scanner;

    /**
     * Конструктор для создания объекта MovieForm.
     * В зависимости от режима (файловый или консольный) выбирает соответствующие объекты для консольного ввода/вывода и пользовательского ввода.
     *
     * @param console Объект ReaderWriter для взаимодействия с консолью. Не должен быть null.
     */

    public MovieForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    @Override
    public Movie build() {
        ReadManager readManager = new ReadManager(console);
        return new Movie(
                readManager.readName(),
                readCoordinates(),
                readManager.readOscarsCount(),
                readManager.readBudget(),
                readMovieGenre(),
                readMpaaRating(),
                readPerson()
        );
    }
    private Coordinates readCoordinates() {
        return new CoordinatesForm(console).build();
    }
    private MovieGenre readMovieGenre() {
        return new MovieGenreForm(console).build();
    }
    private MpaaRating readMpaaRating() {
        return new MpaaRatingForm(console).build();
    }
    private Person readPerson() {
        return new PersonForm(console).build();
    }

}
