package ru.systems1221.whereMyCake.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Модель, представляющая историю питания пользователя за день.
 * Содержит информацию о дате, общем количестве калорий, количестве блюд и списке блюд.
 */
public record DailyDishHistory(

        LocalDate date,
        float totalCalories,
        int dishCount,
        List<DishInfo> dishes
) {
}
