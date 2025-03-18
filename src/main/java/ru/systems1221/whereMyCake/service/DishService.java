package ru.systems1221.whereMyCake.service;

import ru.systems1221.whereMyCake.entity.DishEntity;

import java.util.List;
import java.util.UUID;

public interface DishService {

    List<DishEntity> addDishes(UUID userId, List<DishEntity> dishes);
}
