package ru.systems1221.whereMyCake.service;

import ru.systems1221.whereMyCake.entity.DishEntity;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с блюдами пользователя.
 * Предоставляет метод для добавления списка блюд для пользователя.
 */
public interface DishService {

    /**
     * Добавляет список блюд для пользователя по его ID.
     *
     * @param userId ID пользователя.
     * @param dishes Список блюд для добавления.
     * @return Список добавленных блюд.
     */
    List<DishEntity> addDishes(UUID userId, List<DishEntity> dishes);
}
