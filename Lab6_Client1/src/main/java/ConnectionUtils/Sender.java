package ConnectionUtils;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * Класс отправитель
 * Содержит всю логику соединения с сервером, он отправляет запрос и он же получает на него ответ
 */

public class Sender {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int maxReconnectionAttempts;

    private Socket socket;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    /**
     * Конструктор устанавливает параметры соединения.
     *
     * @param host                  хост сервера
     * @param port                  порт сервера
     * @param reconnectionTimeout   время ожидания перед повторной попыткой соединения
     * @param maxReconnectionAttempts максимальное количество попыток повторного соединения
     */

    public Sender(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
    }
    /**
     * Отправляет запрос на сервер и получает ответ.
     * В случае ошибки пытается повторно подключиться заданное количество раз.
     *
     * @param request запрос, который нужно отправить
     * @return Response ответ от сервера
     * @throws InterruptedException в случае прерывания потока при ожидании повторного соединения
     */

    public Response sendRequest(Request request) throws InterruptedException {
        this.connect();
        int reconnectionAttempts = 0;
        while(true) {
            try {
                // Проверка, что потоки для записи и чтения не null
                if (Objects.isNull(serverWriter) || Objects.isNull(serverReader)) throw new IOException();
                if (request.getCommand() == null) System.err.println("Request is empty");

                // Отправка запроса на сервер
                serverWriter.writeObject(request);
                serverWriter.flush();

                // Чтение ответа от сервера
                Response response = (Response) serverReader.readObject();

                // Отключение от сервера после успешного получения ответа
                this.disconnect();
                return response;
            } catch (IOException ignored) {
                reconnectionAttempts++;
                if(reconnectionAttempts >= maxReconnectionAttempts){
                    ignored.printStackTrace();
                    break;
                }

                // Вывод сообщения о следующей попытке подключения
                System.err.println("Enother attempt in " + reconnectionTimeout / 1000 + " sec");
                // Ожидание перед следующей попыткой подключения
                Thread.sleep(reconnectionTimeout);
                this.connect();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * Устанавливает соединение с сервером.
     */

    public void connect(){
        try {
            socket = new Socket(host, port);
            serverWriter = new ObjectOutputStream(socket.getOutputStream());
            serverReader = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error with connection to server");
        }
    }

    /**
     * Разрывает соединение с сервером и закрывает все потоки.
     */

    public void disconnect() {
        try {
            socket.close();
            serverReader.close();
            serverWriter.close();
        } catch (IOException e) {
            System.err.println("Doesn't connect to server");;
        }
    }
}
