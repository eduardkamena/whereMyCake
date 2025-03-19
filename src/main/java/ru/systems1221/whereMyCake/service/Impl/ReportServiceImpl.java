package ru.systems1221.whereMyCake.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ru.systems1221.whereMyCake.entity.DishEntity;
import ru.systems1221.whereMyCake.entity.DishParameterEntity;
import ru.systems1221.whereMyCake.model.DailyDishHistory;
import ru.systems1221.whereMyCake.model.DailyReport;
import ru.systems1221.whereMyCake.model.DishInfo;
import ru.systems1221.whereMyCake.repository.DishRepository;
import ru.systems1221.whereMyCake.service.CalorieService;
import ru.systems1221.whereMyCake.service.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final DishRepository dishRepository;
    private final CalorieService calorieService;

    @Override
    public DailyReport getDailyUserReport(UUID userId, LocalDate date) {

        List<DishEntity> dishes = dishRepository.findByUsersIdAndDateTimeBetween(
                userId,
                date.atStartOfDay(),
                date.atTime(23, 59, 59)
        );

        if (dishes.isEmpty()) {
            throw new IllegalArgumentException("No dishes found for User with id: " + userId + " and date: " + date);
        } else {
            float totalCalories = dishes.stream()
                    .flatMap(dish -> dish.getParameters().stream())
                    .map(DishParameterEntity::getCalorie)
                    .reduce(0f, Float::sum);

            int dishCount = dishes.size();

            log.info("Successfully get Daily Report for userId: {}, date: {}", userId, date);
            return new DailyReport(totalCalories, dishCount);
        }
    }

    @Override
    public String checkDailyUserCalorie(UUID userId, LocalDate date) {

        float dailyUserCalorie = getDailyUserReport(userId, date).totalCalories();
        float userBaseCalorie = calorieService.getUserCalorie(userId);

        if (dailyUserCalorie < userBaseCalorie) {
            float lessDailyCalorie = userBaseCalorie - dailyUserCalorie;
            return String.format(
                    """
                            Сегодня вы съели %.2f Ккал., а нужно %.2f Ккал.
                            У вас недобор суточных калорий, поднажмите на холодильник!
                            Найдите в нем что-нибудь еще на %.2f Ккал.
                            """,
                    dailyUserCalorie, userBaseCalorie, lessDailyCalorie
            );
        } else if (dailyUserCalorie > userBaseCalorie) {
            float moreDailyCalorie = dailyUserCalorie - userBaseCalorie;
            return String.format(
                    """
                            Сегодня вы съели %.2f Ккал., а нужно %.2f Ккал.
                            У вас перебор суточных калорий на %.2f Ккал.
                            Срочно заприте холодильник на замок!
                            """,
                    dailyUserCalorie, userBaseCalorie, moreDailyCalorie
            );
        } else {
            return String.format(
                    """
                            Сегодня вы съели %.2f Ккал.
                            Это ровно столько, сколько нужно!
                            Идеальное попадание в цель!
                            """,
                    dailyUserCalorie
            );
        }
    }

    @Override
    public List<DailyDishHistory> getDailyDishHistory(UUID userId, LocalDate startDate, LocalDate endDate) {

        List<DishEntity> dishes = dishRepository.findByUsersIdAndDateTimeBetween(
                userId,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );

        if (dishes.isEmpty()) {
            throw new IllegalArgumentException("No dishes found for User with id: " + userId + " and startDate: " + startDate + " and endDate: " + endDate);
        } else {
            Map<LocalDate, List<DishEntity>> dishesByDate = dishes.stream()
                    .collect(Collectors.groupingBy(dish -> dish.getDateTime().toLocalDate()));

            List<DailyDishHistory> history = new ArrayList<>();
            for (Map.Entry<LocalDate, List<DishEntity>> entry : dishesByDate.entrySet()) {
                LocalDate date = entry.getKey();
                List<DishEntity> dailyDishes = entry.getValue();

                float totalCalories = dailyDishes.stream()
                        .flatMap(dish -> dish.getParameters().stream())
                        .map(DishParameterEntity::getCalorie)
                        .reduce(0f, Float::sum);

                int dishCount = dailyDishes.size();

                List<DishInfo> dishInfos = dailyDishes.stream()
                        .map(dish -> new DishInfo(
                                dish.getTitle(),
                                dish.getParameters().stream()
                                        .map(DishParameterEntity::getCalorie)
                                        .reduce(0f, Float::sum),
                                dish.getParameters().stream()
                                        .map(DishParameterEntity::getProtein)
                                        .reduce(0f, Float::sum),
                                dish.getParameters().stream()
                                        .map(DishParameterEntity::getFat)
                                        .reduce(0f, Float::sum),
                                dish.getParameters().stream()
                                        .map(DishParameterEntity::getCarb)
                                        .reduce(0f, Float::sum)
                        ))
                        .collect(Collectors.toList());
                history.add(new DailyDishHistory(date, totalCalories, dishCount, dishInfos));
            }
            history.sort(Comparator.comparing(DailyDishHistory::date));
            return history;
        }
    }
}
