package Utils;

import ConnectionUtils.Client;
import ConnectionUtils.Sender;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ScriptRunner {
    private final static Set<Path> recursionDefense = new HashSet<>();

    public void runScript(String filename) throws AccessDeniedException, FileNotFoundException {
        var path = Path.of(filename).toAbsolutePath();
        if (!Files.isReadable(path))
            throw new AccessDeniedException("There is no such file or program doesn't have enought rights");
        if (ScriptRunner.recursionDefense.contains(path))
            throw new IllegalArgumentException("RECURSION DETECTED");
        ScriptRunner.recursionDefense.add(path);
        var fileIn = new FileReader(path.toFile());
        var fileConsole = new Client();
        var sender = new Sender("localhost", 6732, 3000, 3);
        fileConsole.runApp(fileIn, sender, true);
        System.out.println("the end of file " + filename);
        ScriptRunner.recursionDefense.clear();
    }
}
