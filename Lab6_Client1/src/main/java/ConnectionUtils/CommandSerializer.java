package ConnectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Класс CommandSerializer предоставляет методы для сериализации объектов.
 *
 * @param <T> Тип объекта, который должен быть сериализован. Обязателен для реализации интерфейса Serializable.
 */

public class CommandSerializer {

    /**
     * Сериализует объект в массив байтов.
     *
     * @param <T> Тип объекта, подлежащего сериализации. Объект должен реализовывать интерфейс Serializable.
     * @param object Объект для сериализации. Не должен быть null.
     * @return Массив байтов, представляющий сериализованный объект, или пустой массив байтов в случае ошибки.
     * @throws IllegalArgumentException Если переданный объект равен null.
     */

    public static <T extends Serializable> byte[] serialize(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Object to serialize cannot be null");
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            // Запись объекта в объект ObjectOutputStream
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            // Возвращение массива байтов, представляющего сериализованный объект
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            System.err.println("Serialization error: " + e);
            e.printStackTrace();
            return new byte[0];
        }
    }
}
