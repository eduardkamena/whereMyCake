package ru.systems1221.whereMyCake.exception;

/**
 * Исключение, выбрасываемое при попытке найти пользователя, который не существует.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
