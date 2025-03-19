package ru.systems1221.whereMyCake.exception;

/**
 * Исключение, выбрасываемое при передаче некорректных аргументов в методы.
 */
public class IllegalArgumentException extends RuntimeException {

    public IllegalArgumentException(String message) {
        super(message);
    }
}
