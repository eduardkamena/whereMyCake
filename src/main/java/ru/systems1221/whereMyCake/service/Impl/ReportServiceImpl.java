package ru.systems1221.whereMyCake.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.systems1221.whereMyCake.entity.DishEntity;
import ru.systems1221.whereMyCake.entity.DishParameter;
import ru.systems1221.whereMyCake.model.DailyReport;
import ru.systems1221.whereMyCake.repository.DishRepository;
import ru.systems1221.whereMyCake.service.CalorieService;
import ru.systems1221.whereMyCake.service.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
                    .map(DishParameter::getCalorie)
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
}
