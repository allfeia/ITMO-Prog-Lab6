import Collection.CollectionManager;
import Data.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import Commands.*;

import Utils.*;

public class Main extends Thread{
    public static int PORT = 6732;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    private static final ReaderWriter console = new BlankConsole();

    public static void main(String[] args) {
        if (args.length > 1) {
            try {
                PORT = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignored) {
            }
        }
        CollectionManager collectionManager = new CollectionManager();
        Parser parser = new Parser();

        try {
            ServerLogger.getLogger().info("Creating objects");
            String link = args[0];
            File file = new File(link);
            if (file.exists() && !file.isDirectory()) {
                parser.loadFromCSV();
                collectionManager.checkCollection();
                ServerLogger.getLogger().info("The creation of objects has been completed successfully");
            } else {
                parser.getFileName();
            }
        } catch (FileNotFoundException e) {
            console.write("Do svidaniya!");
            ServerLogger.getLogger().warning("Error in object creation time");
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CommandManager commandManager = new CommandManager(parser);
        commandManager.addCommand(List.of(
                new Help(commandManager),
                new Info(collectionManager),
                new Show(collectionManager),
                new Add(collectionManager),
                new UpdateId(collectionManager),
                new RemoveById(collectionManager),
                new Clear(collectionManager),
                new ExecuteScript(),
                new Exit(),
                new PrintUniqueGenre(collectionManager),
                new AddIfMax(collectionManager),
                new AddIfMin(collectionManager),
                new History(collectionManager)
        ));
        ServerLogger.getLogger().warning("A team manager object has been created");
        RequestHandler requestHandler = new RequestHandler(commandManager);
        ServerLogger.getLogger().warning("A request handler object has been created");
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler, parser);
        ServerLogger.getLogger().warning("A server object has been created");
        server.run();
    }

}
