package ru.systems1221.whereMyCake.exception;

/**
 * Исключение, выбрасываемое при попытке преобразовать значение в элемент перечисления,
 * если такое значение не поддерживается перечислением.
 */
public class DoesNotEnumException extends RuntimeException {

    public DoesNotEnumException(String message) {
        super(message);
    }
}
