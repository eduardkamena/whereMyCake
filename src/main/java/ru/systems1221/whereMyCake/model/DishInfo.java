package ru.systems1221.whereMyCake.model;

/**
 * Модель, представляющая информацию о блюде.
 * Содержит название блюда, количество калорий, белков, жиров и углеводов.
 */
public record DishInfo(

        String title,
        float calorie,
        float protein,
        float fat,
        float carb
) {
}
