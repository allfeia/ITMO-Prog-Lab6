package Utils;

import Console.*;

import java.io.*;
import java.util.ArrayDeque;

/**
 * Класс ExecuteFileManager реализует интерфейс UserInput и управляет очередью файлов для чтения.
 * Позволяет обрабатывать несколько файлов через стек и предотвращать рекурсивное чтение файлов.
 */

public class ExecuteFileManager implements UserInput {

    // Очередь для хранения путей к файлам
    private static final ArrayDeque<String> pathQueue = new ArrayDeque<>();
    // Очередь для хранения BufferedReader, связанных с файлами
    private static final ArrayDeque<BufferedReader> fileReaders = new ArrayDeque<>();

    /**
     * Метод для добавления файла в очередь.
     *
     * @param path Путь к файлу, который требуется добавить.
     * @throws FileNotFoundException Если файл по указанному пути не найден.
     */

    public static void pushFile(String path) throws FileNotFoundException {
        pathQueue.push(new File(path).getAbsolutePath());
        fileReaders.push(new BufferedReader(new InputStreamReader(new FileInputStream(path))));
    }

    public static File getFile() {
        return new File(pathQueue.getFirst());
    }

    public static String readLine() throws IOException {
        return fileReaders.getFirst().readLine();
    }
    public static void popFile() throws IOException {
        fileReaders.getFirst().close();
        // Удаляем текущий BufferedReader из очереди
        fileReaders.pop();
        if(!pathQueue.isEmpty()) {
            pathQueue.pop();
        }
    }

    public static void popRecursion(){
        if(!pathQueue.isEmpty()) {
            pathQueue.pop();
        }
    }

    public static boolean fileRepeat(String path){
        return pathQueue.contains(new File(path).getAbsolutePath());
    }

    @Override
    public String nextLine() {
        try{
            return readLine();
        } catch (IOException e){
            return "";
        }
    }
}
