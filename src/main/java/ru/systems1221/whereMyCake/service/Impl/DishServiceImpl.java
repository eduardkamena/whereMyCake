package ru.systems1221.whereMyCake.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.systems1221.whereMyCake.entity.DishEntity;
import ru.systems1221.whereMyCake.entity.DishParameter;
import ru.systems1221.whereMyCake.entity.UserEntity;
import ru.systems1221.whereMyCake.exception.UserNotFoundException;
import ru.systems1221.whereMyCake.repository.DishRepository;
import ru.systems1221.whereMyCake.repository.UserRepository;
import ru.systems1221.whereMyCake.service.DishService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private static final float PARAMETER_MIN = 0f;
    private static final float PARAMETER_MAX = 1000f;
    private static final float CALORIE_MAX = 3000f;

    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    @Override
    public List<DishEntity> addDishes(UUID userId, List<DishEntity> dishes) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        List<DishEntity> listDishes = new ArrayList<>();
        for (DishEntity dish : dishes) {
            dish.setUsers(user);
            dish.setDateTime(LocalDateTime.now());

            if (dish.getParameters() != null) {
                for (DishParameter parameter : dish.getParameters()) {
                    validateDishParameters(parameter);
                    parameter.setDishes(dish);
                }
            }
            DishEntity savedDish = dishRepository.save(dish);
            listDishes.add(savedDish);
            log.info("Successfully added Dish to List with id: {}", savedDish.getId());
        }
        return listDishes;
    }

    private void validateDishParameters(DishParameter parameter) {
        if (parameter.getCalorie() <= PARAMETER_MIN || parameter.getCalorie() >= CALORIE_MAX) {
            throw new IllegalArgumentException("Parameter calorie must be between 0.01 and 3000 & less than Calorie value");
        }
        if (parameter.getProtein() <= PARAMETER_MIN || parameter.getProtein() >= PARAMETER_MAX || parameter.getProtein() >= parameter.getCalorie()) {
            throw new IllegalArgumentException("Parameter protein must be between 0.01 and 1000 & less than Calorie value");
        }
        if (parameter.getFat() <= PARAMETER_MIN || parameter.getFat() >= PARAMETER_MAX || parameter.getFat() >= parameter.getCalorie()) {
            throw new IllegalArgumentException("Parameter fat must be between 0.01 and 1000 & less than Calorie value");
        }
        if (parameter.getCarb() <= PARAMETER_MIN || parameter.getCarb() >= PARAMETER_MAX || parameter.getCarb() >= parameter.getCalorie()) {
            throw new IllegalArgumentException("Parameter carb must be between 0.01 and 1000 & less than Calorie value");
        }
    }
}
