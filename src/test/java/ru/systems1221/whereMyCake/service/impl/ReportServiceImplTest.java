package ru.systems1221.whereMyCake.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.systems1221.whereMyCake.entity.DishEntity;
import ru.systems1221.whereMyCake.entity.DishParameterEntity;
import ru.systems1221.whereMyCake.entity.UserEntity;
import ru.systems1221.whereMyCake.model.DailyDishHistory;
import ru.systems1221.whereMyCake.model.DailyReport;
import ru.systems1221.whereMyCake.repository.DishRepository;
import ru.systems1221.whereMyCake.repository.UserRepository;
import ru.systems1221.whereMyCake.service.CalorieService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

class ReportServiceImplTest {

    private static final String DISH_TITLE = "Салат Цезарь";
    private static final float DISH_CALORIE = 250f;
    private static final float DISH_PROTEIN = 10f;
    private static final float DISH_FAT = 15f;
    private static final float DISH_CARB = 20f;

    private static final float USER_BASE_CALORIE_MOCK = 2000f;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private CalorieService calorieService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @InjectMocks
    private DishServiceImpl dishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetDailyUserReport() {
        // given
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        DishEntity dish = new DishEntity();
        DishParameterEntity parameter = new DishParameterEntity();
        parameter.setCalorie(DISH_CALORIE);

        dish.setParameters(List.of(parameter));

        // when
        when(dishRepository.findByUsersIdAndDateTimeBetween(
                userId,
                date.atStartOfDay(),
                date.atTime(23, 59, 59)))
                .thenReturn(List.of(dish)
                );

        DailyReport report = reportService.getDailyUserReport(userId, date);

        // then
        assertNotNull(report);
        assertEquals(250, report.totalCalories());
        assertEquals(1, report.dishCount());
    }

    @Test
    void shouldCheckDailyUserCalorie() {
        // given
        UUID userId = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setId(userId);

        LocalDate date = LocalDate.now();

        DishEntity dish = new DishEntity();

        DishParameterEntity parameter = new DishParameterEntity();
        parameter.setCalorie(DISH_CALORIE);
        parameter.setProtein(DISH_PROTEIN);
        parameter.setFat(DISH_FAT);
        parameter.setCarb(DISH_CARB);

        dish.setParameters(List.of(parameter));

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(dishRepository.save(dish)).thenReturn(dish);

        List<DishEntity> listDish = dishService.addDishes(userId, List.of(dish));
        when(calorieService.getUserCalorie(userId)).thenReturn(USER_BASE_CALORIE_MOCK);

        when(dishRepository.findByUsersIdAndDateTimeBetween(
                userId,
                date.atStartOfDay(),
                date.atTime(23, 59, 59)))
                .thenReturn(listDish);

        String result = reportService.checkDailyUserCalorie(userId, date);

        // then
        assertTrue(result.contains("У вас недобор суточных калорий, поднажмите на холодильник!"));
    }

    @Test
    void shouldGetDailyDishHistory() {
        // given
        UUID userId = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2025, 3, 19);
        LocalDate endDate = LocalDate.of(2025, 3, 20);

        DishEntity dish = new DishEntity();
        DishParameterEntity parameter1 = new DishParameterEntity();
        parameter1.setCalorie(DISH_CALORIE);
        parameter1.setProtein(DISH_PROTEIN);
        parameter1.setFat(DISH_FAT);
        parameter1.setCarb(DISH_CARB);

        dish.setTitle(DISH_TITLE);
        dish.setParameters(List.of(parameter1));
        dish.setDateTime(LocalDateTime.of(2025, 3, 20, 0, 40));

        // when
        when(dishRepository.findByUsersIdAndDateTimeBetween(
                userId,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        )).thenReturn(List.of(dish));

        List<DailyDishHistory> history = reportService.getDailyDishHistory(
                userId,
                startDate,
                endDate);

        // then
        assertNotNull(history);
        assertEquals(1, history.size());

        DailyDishHistory day = history.get(0);
        assertEquals(LocalDate.of(2025, 3, 20), day.date());
        assertEquals(250, day.totalCalories());
        assertEquals(1, day.dishCount());
        assertEquals("Салат Цезарь", day.dishes().get(0).title());
    }

    @Test
    void shouldThrowsWhenGetDailyDishHistory() {
        UUID userId = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2025, 3, 19);
        LocalDate endDate = LocalDate.of(2025, 3, 20);

        when(dishRepository.findByUsersIdAndDateTimeBetween(
                userId,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        )).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class, () ->
                reportService.getDailyDishHistory(userId, startDate, endDate)
        );
    }
}
