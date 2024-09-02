package Utils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import Collection.CollectionManager;
import ConnectionUtils.*;

/**
 * Основной класс сервера, предназначенный для обработки клиентских запросов.
 * Он открывает серверный сокет, принимает подключения и обрабатывает запросы клиентов.
 */

public class Server {
    private int port;
    private ServerSocketChannel ss;

    BufferedInputStream bf = new BufferedInputStream(System.in);
    BufferedReader scanner = new BufferedReader(new InputStreamReader(bf));
    private static CollectionManager collectionManager;

    private final ExecutorService clientRequestFixedThreadPool = Executors.newFixedThreadPool(10);

    public Server(int port, CollectionManager collectionManager) throws SocketException {
        this.port = port;
        this.collectionManager = collectionManager;
    }

    public void run() {
        try {
            openServerSocket();
            ServerLogger.getLogger().info("Why are you called me..");

            while (true) {
                if (scanner.ready()) {
                    String line = scanner.readLine();
                    if (line.equals("save")) {
                        collectionManager.save();
                        //Parser.saveToCSV();
                        ServerLogger.getLogger().info("Elements have been successfully saved");
                    } else if (line.equals("exit")) {
                        collectionManager.save();
                        //Parser.saveToCSV();
                        ServerLogger.getLogger().info("Elements have been successfully saved");
                        System.exit(0);
                    } else System.out.println("no ansewr.. I'm tired");
                }

                // Принимаем входящее соединение от клиента
                SocketChannel clientSocket = ss.accept();
                if (clientSocket != null) {
                   new Thread(() -> processClientRequest(clientSocket)).start();
                }
            }
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Error with server's working" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void openServerSocket() {
        try {
            // Создаем серверный сокет и привязываем его к порту
            ss = ServerSocketChannel.open();
            ss.bind(new InetSocketAddress(port));
            // Конфигурируем сокет в неблокирующий режим
            ss.configureBlocking(false);
        } catch (IOException exception) {
            ServerLogger.getLogger().warning("Error during using port");
        }
    }

    private void processClientRequest(SocketChannel clientSocket) {

        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream())) {

            // Читаем запрос от клиента
            Request userRequest = (Request) clientReader.readObject();
            ServerLogger.getLogger().info("Get request " + userRequest);

            // Создаем менеджер команд и выполняем команду
            clientRequestFixedThreadPool.submit(() -> {
                try {
                    CommandManager commandManager = new CommandManager();
                    var responseToUser = commandManager.runCommand(userRequest, this.collectionManager);

                    if (responseToUser != null) {

                        // Отправляем ответ клиенту
                        ForkJoinPool.commonPool().execute(() -> {
                            try {
                                clientWriter.writeObject(responseToUser);
                                ServerLogger.getLogger().info("Send response " + responseToUser.getResult());
                                clientWriter.flush();
                            } catch (IOException e) {
                                ServerLogger.getLogger().warning("I/O error during sending response: " + e.getMessage());
                            }
                        });
                    } else {
                        ServerLogger.getLogger().warning("Response is null. Cannot send to client");
                    }
                } catch (Exception e){
                    ServerLogger.getLogger().warning("Error processing client request: " + e.getMessage());
                }
                });

        } catch (ClassNotFoundException | InvalidClassException | NotSerializableException e) {
            ServerLogger.getLogger().warning("Error during collaboration with client");
        } catch (IOException exception) {
            ServerLogger.getLogger().warning("I/O error " + exception.getMessage());
        } finally {
            // Закрываем клиентский сокет
            try {
                clientSocket.close();
            } catch (IOException e) {
                ServerLogger.getLogger().warning("Error during closing client's socket");
            }
        }
    }
}