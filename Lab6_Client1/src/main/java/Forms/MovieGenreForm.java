package Forms;

import Console.ReaderWriter;
import Data.MovieGenre;
import Console.*;
import Utils.ExecuteFileManager;
import Utils.ReadManager;

public class MovieGenreForm extends Form<MovieGenre>{
    private final ReaderWriter console;
    private final UserInput scanner;
    public MovieGenreForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    @Override
    public MovieGenre build() {
        ReadManager readManager = new ReadManager(console);
        return readManager.readMovieGenre();
    }
}
