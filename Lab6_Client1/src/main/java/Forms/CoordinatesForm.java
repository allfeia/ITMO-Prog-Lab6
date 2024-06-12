package Forms;

import Console.*;
import Data.Coordinates;
import Utils.ExecuteFileManager;
import Utils.ReadManager;

public class CoordinatesForm extends Form<Coordinates>{
    private final ReaderWriter console;
    private final UserInput scanner;
    public CoordinatesForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    @Override
    public Coordinates build() {
        ReadManager readManager = new ReadManager(console);
        return new Coordinates(
                readManager.readCoordinateX(),
                readManager.readCoordinateY()
        );
    }
}
