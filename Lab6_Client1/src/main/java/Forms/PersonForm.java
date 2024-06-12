package Forms;

import Console.*;
import Data.Person;
import Utils.ExecuteFileManager;
import Utils.ReadManager;

public class PersonForm extends Form<Person>{
    private final ReaderWriter console;
    private final UserInput scanner;
    public PersonForm(ReaderWriter console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }
    @Override
    public Person build() {
        ReadManager readManager = new ReadManager(console);
        return new Person(
                readManager.readOperatorsName(),
                readManager.readWeight(),
                readManager.readEyeColor()
        );
    }
}
