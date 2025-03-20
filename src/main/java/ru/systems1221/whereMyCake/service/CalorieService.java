package ru.systems1221.whereMyCake.service;

import java.util.UUID;

/**
 * Сервис для работы с дневной нормой калорий пользователя.
 * Предоставляет метод для получения дневной нормы калорий по ID пользователя.
 */
public interface CalorieService {

    /**
     * Возвращает дневную норму калорий для пользователя по его ID.
     *
     * @param userId ID пользователя.
     * @return Дневная норма калорий.
     */
    Float getUserCalorie(UUID userId);
}
