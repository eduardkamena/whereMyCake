package ru.systems1221.whereMyCake.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.systems1221.whereMyCake.entity.DishEntity;
import ru.systems1221.whereMyCake.entity.DishParameter;
import ru.systems1221.whereMyCake.model.DailyReport;
import ru.systems1221.whereMyCake.repository.DishRepository;
import ru.systems1221.whereMyCake.service.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final DishRepository dishRepository;

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
}
