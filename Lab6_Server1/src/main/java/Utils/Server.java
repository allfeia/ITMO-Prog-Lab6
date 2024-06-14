package Utils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

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
    private final CollectionManager collectionManager;

    public Server(int port, CollectionManager collectionManager) {
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
                        Parser.saveToCSV();
                        ServerLogger.getLogger().info("Elements has been successfully saved");
                    } else if (line.equals("exit")) {
                        Parser.saveToCSV();
                        ServerLogger.getLogger().info("Elements has been successfully saved");
                        System.exit(0);
                    } else System.out.println("no ansewr.. I'm tired");
                }

                SocketChannel clientSocket = ss.accept();
                if (clientSocket != null) {
                    processClientRequest(clientSocket);
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
            ss = ServerSocketChannel.open();
            ss.bind(new InetSocketAddress(port));
            ss.configureBlocking(false);
        } catch (IOException exception) {
            ServerLogger.getLogger().warning("Error during using port");
        }
    }

    private void processClientRequest(SocketChannel clientSocket) {
        Request userRequest;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream())) {

            userRequest = (Request) clientReader.readObject();
            ServerLogger.getLogger().info("Get request " + userRequest);
            CommandManager commandManager = new CommandManager();
            var responseToUser = commandManager.runCommand(userRequest, this.collectionManager);
            clientWriter.writeObject(responseToUser);
            ServerLogger.getLogger().info("Send response " + responseToUser.getResult());
            clientWriter.flush();
        } catch (ClassNotFoundException | InvalidClassException | NotSerializableException e) {
            ServerLogger.getLogger().warning("Error during collaboration with client");
        } catch (IOException exception) {
            ServerLogger.getLogger().warning("I/O error " + exception.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                ServerLogger.getLogger().warning("Error during closing client's socket");
            }
        }
    }
}
