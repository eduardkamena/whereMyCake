package ru.systems1221.whereMyCake.model;

/**
 * Модель, представляющая дневной отчет о питании пользователя.
 * Содержит информацию об общем количестве калорий и количестве блюд.
 */
public record DailyReport(

        float totalCalories,
        int dishCount
) {
}
