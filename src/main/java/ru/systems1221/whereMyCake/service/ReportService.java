package ru.systems1221.whereMyCake.service;

import ru.systems1221.whereMyCake.model.DailyDishHistory;
import ru.systems1221.whereMyCake.model.DailyReport;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с отчетами о питании пользователя.
 * Предоставляет методы для получения дневного отчета, проверки дневной нормы калорий и истории питания за период.
 */
public interface ReportService {

    /**
     * Возвращает дневной отчет по калориям для пользователя за указанную дату.
     *
     * @param userId ID пользователя.
     * @param date   Дата отчета.
     * @return Дневной отчет.
     */
    DailyReport getDailyUserReport(UUID userId, LocalDate date);

    /**
     * Проверяет, соответствует ли фактическое количество калорий дневной норме пользователя.
     *
     * @param userId ID пользователя.
     * @param date   Дата проверки.
     * @return Сообщение с результатом проверки.
     */
    String checkDailyUserCalorie(UUID userId, LocalDate date);

    /**
     * Возвращает историю питания пользователя за указанный период.
     *
     * @param userId    ID пользователя.
     * @param startDate Начальная дата периода.
     * @param endDate   Конечная дата периода.
     * @return Список дневных отчетов за период.
     */
    List<DailyDishHistory> getDailyDishHistory(UUID userId, LocalDate startDate, LocalDate endDate);
}
