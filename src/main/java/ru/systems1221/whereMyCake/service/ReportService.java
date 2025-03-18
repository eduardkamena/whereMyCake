package ru.systems1221.whereMyCake.service;

import ru.systems1221.whereMyCake.model.DailyReport;

import java.time.LocalDate;
import java.util.UUID;

public interface ReportService {

    DailyReport getDailyUserReport(UUID userId, LocalDate date);
}
