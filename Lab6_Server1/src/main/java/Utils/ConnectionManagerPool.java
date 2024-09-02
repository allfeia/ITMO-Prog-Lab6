package Utils;

import ConnectionUtils.Response;

import java.io.ObjectOutputStream;

//Рекорд для хранения в коллекции всех пулов для проверки выполнения

public record ConnectionManagerPool(Response response, ObjectOutputStream objectOutputStream){};
