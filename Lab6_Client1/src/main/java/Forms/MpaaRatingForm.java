package Forms;

import Data.MpaaRating;
import Console.*;
import Utils.ExecuteFileManager;
import Utils.ReadManager;

public class MpaaRatingForm extends Form<MpaaRating>{
    private final ReaderWriter console;
    private final UserInput scanner;
    public MpaaRatingForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    @Override
    public MpaaRating build() {
        ReadManager readManager = new ReadManager(console);
        return readManager.readMpaaRating();
    }
}
