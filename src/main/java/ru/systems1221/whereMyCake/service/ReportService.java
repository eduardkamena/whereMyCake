package ru.systems1221.whereMyCake.service;

import ru.systems1221.whereMyCake.model.DailyDishHistory;
import ru.systems1221.whereMyCake.model.DailyReport;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReportService {

    DailyReport getDailyUserReport(UUID userId, LocalDate date);

    String checkDailyUserCalorie(UUID userId, LocalDate date);

    List<DailyDishHistory> getDailyDishHistory(UUID userId, LocalDate startDate, LocalDate endDate);
}
