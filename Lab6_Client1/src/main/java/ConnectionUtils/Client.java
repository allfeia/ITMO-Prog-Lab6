package ConnectionUtils;

import Console.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * Класс Client представляет собой клиента, который подключается к серверу,
 * отправляет запросы и получает ответы. Он поддерживает повторные попытки
 * подключения в случае разрыва соединения.
 */

public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private Console console;
    private Socket socket;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    /**
     * Конструктор класса Client.
     *
     * @param host Адрес сервера
     * @param port Порт сервера
     * @param reconnectionTimeout Таймаут между попытками переподключения
     * @param maxReconnectionAttempts Максимальное количество попыток переподключения
     * @param console Консоль для вывода сообщений
     */

//    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, Console console) {
//        this.host = host;
//        this.port = port;
//        this.reconnectionTimeout = reconnectionTimeout;
//        this.maxReconnectionAttempts = maxReconnectionAttempts;
//        this.console = console;
//    }


    /**
     * Отправляет запрос на сервер и ждет ответа. В случае сбоя соединения пытается переподключиться.
     *
     * @param request Запрос, который необходимо отправить
     * @return Ответ от сервера
     */

    public Response sendAndAskResponse(Request request){
        while (true) {
            try {
                // Проверка на корректность потоков передачи данных
                if(Objects.isNull(serverWriter) || Objects.isNull(serverReader)) throw new IOException();
                if (request.isEmpty()) return new Response(ResponseStatus.WRONG_ARGUMENTS, "Response is empty");

                serverWriter.writeObject(request);
                serverWriter.flush();

                Response response = (Response) serverReader.readObject();

                //this.disconnectFromServer();
                reconnectionAttempts = 0;
                return response;
            } catch (IOException e) {

                if (reconnectionAttempts == 0){
                    connectToServer();
                    reconnectionAttempts++;
                    continue;
                } else {
                    console.printError("The connection to the server is broken");
                }
                try {
                    reconnectionAttempts++;
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        console.printError("The maximum number of attempts to connect to the server has been exceeded");
                        return new Response(ResponseStatus.EXIT);
                    }
                    console.write("Retry via " + reconnectionTimeout / 1000 + " sec");
                    Thread.sleep(reconnectionTimeout);
                    connectToServer();
                } catch (Exception exception) {
                    console.printError("The attempt to connect to the server was unsuccessful");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Подключение к серверу.
     */

    public void connectToServer(){
        try{
            if(reconnectionAttempts > 0) console.write("Attempt to reconnect");
            this.socket = new Socket(host, port);
            this.serverWriter = new ObjectOutputStream(socket.getOutputStream());
            this.serverReader = new ObjectInputStream(socket.getInputStream());

        } catch (IllegalArgumentException e){
            console.printError("The server address was entered incorrectly");
        } catch (IOException e) {
            console.printError("An error occurred while connecting to the server");
        }
    }

    public void disconnectFromServer(){
        try {
            this.socket.close();
            serverWriter.close();
            serverReader.close();
        } catch (IOException e) {
            console.printError("Not connected to the server");
        }
    }
}
