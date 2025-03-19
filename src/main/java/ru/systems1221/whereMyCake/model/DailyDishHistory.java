package ru.systems1221.whereMyCake.model;

import java.time.LocalDate;
import java.util.List;

public record DailyDishHistory(

        LocalDate date,
        float totalCalories,
        int dishCount,
        List<DishInfo> dishes
) {
}
