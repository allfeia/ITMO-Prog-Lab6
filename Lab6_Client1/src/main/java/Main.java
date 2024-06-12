import ConnectionUtils.Client;
import Console.BlankConsole;
import Console.ReaderWriter;
import Console.Console;
import Errors.IllegalArgumentsException;
import Console.RuntimeManager;

import java.util.Scanner;


public class Main {
        private static String host;
        private static int port;
        private static ReaderWriter console = new BlankConsole();

        public static boolean parseHostPort(String[] args){
            try{
                if(args.length != 2) throw new IllegalArgumentsException("Pass the host and port as arguments " +
                        "command line in the format <host> <port>");
                host = args[0];
                port = Integer.parseInt(args[1]);
                if(port < 0) throw new IllegalArgumentsException("Port must be integer");
                return true;
            } catch (IllegalArgumentsException e) {
                console.printError(e.getMessage());
            }
            return false;
        }

        public static void main(String[] args) {
            System.out.println("I'm listen u, my heart");
            if (!parseHostPort(args)) return;
            Console console = new Console();
            Client client = new Client(host, port, 6732, 5, console);
            new RuntimeManager(console, new Scanner(System.in), client).interactiveMode();
        }
    }
