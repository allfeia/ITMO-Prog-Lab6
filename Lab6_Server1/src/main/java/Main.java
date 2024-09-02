import Collection.CollectionManager;

import Data.Movie;
import DataBase.DataBaseManager;
import DataBase.DataBaseParser;
import Utils.*;

import java.sql.Connection;
import java.util.HashSet;

public class Main{
    private final static Integer serverPort = 6732;
    public static void main(String[] args) {
        DataBaseManager dataBaseManager = new DataBaseManager();
         Connection conn = dataBaseManager.connect();
        //dataBaseManager.selectAllObj(conn);
        DataBaseParser dataBaseParser = new DataBaseParser();
        HashSet<Movie> collection = dataBaseParser.load();
        try {
            CollectionManager collectionManager = new CollectionManager();
            Server server = new Server(serverPort, collectionManager);
            server.run();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


}
//TODO: Для многопоточной отправки ответа использовать ForkJoinPool
// (response при отправке ответа становится null, при этом на сервере ответ формируется)
//TODO: токены
//TODO: авторизация
//TODO: уведомлять всех пользователей о подключении/отключении др. пользователей
//TODO: нельзя изменять чужие объекты
//TODO: ебучее хэширование крипты