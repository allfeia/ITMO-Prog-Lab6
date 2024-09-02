import ConnectionUtils.Client;
import ConnectionUtils.Sender;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Main {
    private static String host;
    private static int port;

    public static void main(String[] args) {
        System.out.println("I'm listen u, my heart");
        Client client = new Client();
        Sender sender = new Sender("localhost", 6732, 50000, 3);
        client.runApp(new InputStreamReader(System.in), sender, false);
    }

}
