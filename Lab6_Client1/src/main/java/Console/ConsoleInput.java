package Console;

import Utils.ScannerManager;

import java.util.Scanner;

public class ConsoleInput implements UserInput{
    private static final Scanner userScanner = ScannerManager.getUserScanner();
    @Override
    public String nextLine() {
        return userScanner.nextLine();
    }
}
