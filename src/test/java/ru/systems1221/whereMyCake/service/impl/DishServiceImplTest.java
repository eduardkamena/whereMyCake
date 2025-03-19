package ru.systems1221.whereMyCake.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.systems1221.whereMyCake.entity.DishEntity;
import ru.systems1221.whereMyCake.entity.DishParameterEntity;
import ru.systems1221.whereMyCake.entity.UserEntity;
import ru.systems1221.whereMyCake.repository.DishRepository;
import ru.systems1221.whereMyCake.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


class DishServiceImplTest {

    private static final String DISH_TITLE = "Салат Цезарь";
    private static final float DISH_CALORIE = 250f;
    private static final float DISH_PROTEIN = 10f;
    private static final float DISH_FAT = 15f;
    private static final float DISH_CARB = 20f;

    private static final float DISH_CALORIE_INCORRECT = 0f;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DishServiceImpl dishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddDishes() {
        // given
        UUID userId = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(userId);

        DishEntity dish = new DishEntity();

        DishParameterEntity parameter = new DishParameterEntity();
        parameter.setCalorie(DISH_CALORIE);
        parameter.setProtein(DISH_PROTEIN);
        parameter.setFat(DISH_FAT);
        parameter.setCarb(DISH_CARB);

        dish.setTitle(DISH_TITLE);
        dish.setParameters(List.of(parameter));

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(dishRepository.save(dish)).thenReturn(dish);

        List<DishEntity> result = dishService.addDishes(userId, List.of(dish));

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Салат Цезарь", result.get(0).getTitle());
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void shouldThrowsWhenAddDishes() {
        // given
        UUID userId = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(userId);

        DishEntity dish = new DishEntity();

        DishParameterEntity parameter = new DishParameterEntity();
        parameter.setCalorie(DISH_CALORIE_INCORRECT);

        dish.setTitle(DISH_TITLE);
        dish.setParameters(List.of(parameter));

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // then
        assertThrows(IllegalArgumentException.class, () -> dishService.addDishes(userId, List.of(dish)));
    }
}
