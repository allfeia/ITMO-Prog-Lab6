package Console;

import ConnectionUtils.Client;
import ConnectionUtils.Request;
import ConnectionUtils.Response;
import ConnectionUtils.ResponseStatus;
import Data.Movie;
import Errors.FileModeException;
import Forms.MovieForm;
import Utils.ExecuteFileManager;
import Errors.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class RuntimeManager {
    private final Console console;
    private final Scanner userScanner;
    private final Client client;

    public RuntimeManager(Console console, Scanner userScanner, Client client) {
        this.console = console;
        this.userScanner = userScanner;
        this.client = client;
    }

    public void interactiveMode() {
        while (true) {
            try {
                if (!userScanner.hasNext()) throw new ExitObligedException();
                String[] userCommand = (userScanner.nextLine().trim().toLowerCase() + " ").split(" ", 2); // прибавляем пробел, чтобы split выдал два элемента в массиве
                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));
                this.printResponse(response);
                switch (response.getStatus()) {
                    case ASK_OBJECT -> {
                        Movie movie = new MovieForm(console).build();
                        if (!movie.validate()) throw new InvalidFormException();
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        userCommand[1].trim(),
                                        movie));
                        if (newResponse.getStatus() != ResponseStatus.OK) {
                            console.printError(newResponse.getResponse());
                        } else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> throw new ExitObligedException();
                    case EXECUTE_SCRIPT -> {
                        Console.setFileMode(true);
                        this.fileExecution(response.getResponse());
                        Console.setFileMode(false);
                    }
                    default -> {
                    }
                }
            } catch (InvalidFormException err) {
                console.printError("The fields are not valid! The object has not been created");
            } catch (NoSuchElementException exception) {
                console.printError("No user input detected");
            } catch (ExitObligedException exitObliged) {
                console.write("Do svidanya!");
                return;
            }
        }
    }

    private void printResponse(Response response) {
        switch (response.getStatus()) {
            case OK -> {
                if ((Objects.isNull(response.getCollection()))) {
                    console.write(response.getResponse());
                } else {
                    console.write(response.getResponse() + "\n" + response.getCollection().toString());
                }
            }
            case ERROR -> console.printError(response.getResponse());
            case WRONG_ARGUMENTS -> console.printError("Incorrect use of the command");
            default -> {
            }
        }
    }

    private void fileExecution(String args) throws ExitObligedException {
        if (args == null || args.isEmpty()) {
            console.printError("The path is not recognized");
            return;
        } else args = args.trim();
        try {
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                String[] userCommand = (line + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].isBlank()) return;
                if (userCommand[0].equals("execute_script")) {
                    if (ExecuteFileManager.fileRepeat(userCommand[1])) {
                        console.printError("A recursion was found along the path " + new File(userCommand[1]).getAbsolutePath());
                        continue;
                    }
                }
                //console.write("Executing command: " + userCommand[0]);
                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));
                this.printResponse(response);
                switch (response.getStatus()) {
                    case ASK_OBJECT -> {
                        Movie movie;
                        try {
                            movie = new MovieForm(console).build();
                            if (!movie.validate()) throw new FileModeException();
                        } catch (FileModeException err) {
                            console.printError("The fields in the file are not valid! The object has not been created");
                            continue;
                        }
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        userCommand[1].trim(),
                                        movie));
                        if (newResponse.getStatus() != ResponseStatus.OK) {
                            console.printError(newResponse.getResponse());
                        } else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> throw new ExitObligedException();
                    case EXECUTE_SCRIPT -> {
                        this.fileExecution(response.getResponse());
                        ExecuteFileManager.popRecursion();
                    }
                    default -> {
                    }
                }
            }
            ExecuteFileManager.popFile();
        } catch (FileNotFoundException fileNotFoundException) {
            console.printError("There is no such file");
        } catch (IOException e) {
            console.printError("I/O error");
        }
    }
}