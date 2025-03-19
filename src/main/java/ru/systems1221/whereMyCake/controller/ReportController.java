package ru.systems1221.whereMyCake.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.systems1221.whereMyCake.model.DailyDishHistory;
import ru.systems1221.whereMyCake.model.DailyReport;
import ru.systems1221.whereMyCake.service.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/reports")
@Tag(name = "Контроллер работы с отчетами",
        description = "API для работы с отчетами о питании пользователя")
@Slf4j
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(
            summary = "Получить дневной отчет по калориям",
            description = "Возвращает суммарное количество калорий и количество приемов пищи за указанный день."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Дневной отчет успешно получен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DailyReport.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "totalCalories": 1500.0,
                                      "dishCount": 3
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос (например, неверные данные)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "Validation for report error: Invalid input data")
                    )
            )
    })
    @GetMapping(path = "/daily/total")
    public ResponseEntity<?> getDailyUserReport(
            @Parameter(
                    description = "ID пользователя",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @RequestParam UUID userId,

            @Parameter(
                    description = "Дата отчета (в формате YYYY-MM-DD)",
                    required = true,
                    example = "2025-03-18"
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        log.info("getDailyReport method called for User with id: {} and date: {}", userId, date);
        try {
            DailyReport report = reportService.getDailyUserReport(userId, date);
            return ResponseEntity.ok(report);
        } catch (IllegalArgumentException e) {
            log.error("Validation for report error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Проверить дневную норму калорий",
            description = "Сравнивает фактическое количество калорий с дневной нормой пользователя."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Проверка выполнена успешно",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    "Сегодня вы съели 1200.0 Ккал., а нужно 1500.0 Ккал.
                                    У вас недобор суточных калорий, поднажмите на холодильник!
                                    Найдите в нем что-нибудь еще на 300.0 Ккал."
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос (например, неверные данные)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "Validation for checking error: Invalid input data")
                    )
            )
    })
    @GetMapping(path = "/daily/check")
    public ResponseEntity<?> checkDailyUserCalorie(
            @Parameter(
                    description = "ID пользователя",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @RequestParam UUID userId,

            @Parameter(
                    description = "Дата проверки (в формате YYYY-MM-DD)",
                    required = true,
                    example = "2025-03-18"
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        log.info("checkDailyUserCalorie method called for User with id: {} and date: {}", userId, date);
        try {
            String check = reportService.checkDailyUserCalorie(userId, date);
            return ResponseEntity.ok(check);
        } catch (IllegalArgumentException e) {
            log.error("Validation for checking error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Получить историю питания за период",
            description = "Возвращает историю питания пользователя за указанный период."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "История питания успешно получена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DailyDishHistory.class),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "date": "2025-03-18",
                                        "totalCalories": 1200.0,
                                        "dishCount": 3,
                                        "dishes": [
                                          {
                                            "title": "Салат Цезарь",
                                            "calorie": 250,
                                            "protein": 10,
                                            "fat": 15,
                                            "carb": 20
                                          }
                                        ]
                                      }
                                    ]
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос (например, неверные данные)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "Validation for history error: Invalid input data")
                    )
            )
    })
    @GetMapping(path = "/history")
    public ResponseEntity<?> getDailyDishHistory(
            @Parameter(
                    description = "ID пользователя",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @RequestParam UUID userId,

            @Parameter(
                    description = "Начальная дата периода (в формате YYYY-MM-DD)",
                    required = true,
                    example = "2025-03-18"
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

            @Parameter(
                    description = "Конечная дата периода (в формате YYYY-MM-DD)",
                    required = true,
                    example = "2025-03-19"
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        log.info("getDailyDishHistory method called for User with id: {}, startDate: {}, endDate: {}", userId, startDate, endDate);
        try {
            List<DailyDishHistory> history = reportService.getDailyDishHistory(userId, startDate, endDate);
            return ResponseEntity.ok(history);
        } catch (IllegalArgumentException e) {
            log.error("Validation for history error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
