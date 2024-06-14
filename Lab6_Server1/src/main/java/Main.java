import Collection.CollectionManager;
import Data.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import Commands.*;

import Utils.*;

public class Main{
    public static void main(String[] args) {
        // Проверка аргументов командной строки
        if (args.length == 0) {
            System.out.println("The application does not work without a file name to save, " +
                    "please restart the application by specifying the correct file path");
            System.exit(0);
        } else if (args.length > 1) System.out.println("you specified several arguments at startup, " +
                "the application will try to use the first one as the file name");

        Repository repository = new Repository();
        var filename = args[0]; // Получаем имя файла из аргументов
        try {
            Parser.loadFromCSV();
            System.out.println("File has been successfully read");
        } catch (Exception e) {
            System.out.println("File is empty");
            repository = new Repository();
        }

        CollectionManager collectionManager = new CollectionManager(repository, filename);
        var server = new Server(6732, collectionManager);
        server.run();
    }

}
