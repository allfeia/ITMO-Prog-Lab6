package Utils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import ConnectionUtils.*;
import Errors.ConnectionErrorException;
import Errors.OpeningServerException;

/**
 * Основной класс сервера, предназначенный для обработки клиентских запросов.
 * Он открывает серверный сокет, принимает подключения и обрабатывает запросы клиентов.
 */

public class Server {
    private int port;
    private int soTimeout;
    private ReaderWriter console;
    private ServerSocketChannel ss;
    private SocketChannel socketChannel;
    private RequestHandler requestHandler;

    /**
     * Скaннер для чтения команд из консоли.
     * Он использует BufferedInputStream и BufferedReader для чтения данных из System.in.
     */

    BufferedInputStream bf = new BufferedInputStream(System.in);

    BufferedReader scanner = new BufferedReader(new InputStreamReader(bf));
    private Parser parser;


    /**
     * Конструктор для создания объекта сервера.
     *
     * @param port Порт, на котором сервер будет слушать входящие соединения.
     * @param soTimeout Тайм-аут сокета.
     * @param handler Обработчик запросов.
     * @param parser Парсер для обработки данных.
     */

    public Server(int port, int soTimeout, RequestHandler handler, Parser parser) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.console = new BlankConsole();
        this.requestHandler = handler;
        this.parser = new Parser();
    }

    /**
     * Основной метод запуска сервера.
     * Он открывает серверный сокет и затем в бесконечном цикле обрабатывает клиентские соединения.
     * Также обрабатываются команды, введенные в консоли серверной части.
     */

    public void run(){
        try{
            openServerSocket();
            ServerLogger.getLogger().info("Connection with client created");
            // Бесконечный цикл для прослушивания и обработки клиентских соединений.
            while (true) {
                try {
                    // Проверка на ввод команд с консоли
                    if (scanner.ready()) {
                        String line = scanner.readLine();
                        if (line.equals("save")) {
                            Parser.saveToCSV();
                            ServerLogger.getLogger().info("Collection has been saved");
                        }
                    }
                } catch (IOException ignored) {} catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // Прием клиентского соединения
                try (SocketChannel clientSocket = ss.accept()) {
                    if (clientSocket != null){
                        processClientRequest(clientSocket);
                    }

                } catch (IOException exception) {
                    console.printError("An error occurred while trying to terminate the connection with the client");
                    ServerLogger.getLogger().warning("An error occurred while trying to terminate the connection with the client");
                }
            }

        } catch (OpeningServerException e) {
            console.printError("The server cannot be started");
            ServerLogger.getLogger().warning("The server cannot be started");
        } catch (NullPointerException ex){
            ServerLogger.getLogger().warning("IT'S LITERALLY FAIL");
        }
    }

    /**
     * Метод для открытия серверного сокета.
     * Он создает и конфигурирует ServerSocketChannel.
     *
     * @throws OpeningServerException Исключение, выбрасывающееся при ошибках открытия сокета.
     */

    private void openServerSocket() throws OpeningServerException{
        try {
            ss = ServerSocketChannel.open();
            ServerLogger.getLogger().info("Channel created");
            ss.bind(new InetSocketAddress(port));
            ss.configureBlocking(false);
            ServerLogger.getLogger().info("Socket's channel opened");

        } catch (IllegalArgumentException exception) {
            console.printError("Port '" + port + "' it is beyond the limits of possible values");
            ServerLogger.getLogger().warning("Port is beyond the limits of possible values");
            throw new OpeningServerException();
        } catch (IOException exception) {
            ServerLogger.getLogger().warning("An error occurred while trying to use the port");
            console.printError("An error occurred while trying to use the port: '" + port + "'");
            throw new OpeningServerException();
        }
    }

    /**
     * Метод для обработки клиентского запроса.
     *
     * @param clientSocket клиентский сокет, через который идет взаимодействие с клиентом.
     */

    private void processClientRequest(SocketChannel clientSocket) {

        Request userRequest;
        Response responseToUser;
        try {
            ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
            ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            ServerLogger.getLogger().info("Input output streams are open");

            userRequest = (Request) clientReader.readObject();
            ServerLogger.getLogger().info("A request was received with the command" + userRequest.getCommandName() + "," + userRequest);
            console.write(userRequest.toString());
            responseToUser = requestHandler.handle(userRequest);
            clientWriter.writeObject(responseToUser);
            ServerLogger.getLogger().info("A reply has been sent:" + responseToUser);
            clientWriter.flush();

        } catch (ClassNotFoundException | InvalidClassException | NotSerializableException e) {
            ServerLogger.getLogger().info("The client has been successfully disconnected from the server");
        }catch (IOException e){
            ServerLogger.getLogger().warning("Error connectid with input output " + e.getMessage());
        }finally {
            try {
                clientSocket.close();
            }catch (IOException e){
                ServerLogger.getLogger().warning("Error with closing client's socket");
            }
        }
    }
}
