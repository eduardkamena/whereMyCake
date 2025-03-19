package ru.systems1221.whereMyCake.service;

import ru.systems1221.whereMyCake.entity.UserEntity;

/**
 * Сервис для работы с пользователями.
 * Предоставляет метод для создания нового пользователя.
 */
public interface UserService {

    /**
     * Создает нового пользователя.
     *
     * @param user Данные пользователя.
     * @return Созданный пользователь.
     */
    UserEntity createUser(UserEntity user);
}
