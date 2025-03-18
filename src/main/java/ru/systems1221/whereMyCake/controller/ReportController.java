package ru.systems1221.whereMyCake.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.systems1221.whereMyCake.model.DailyReport;
import ru.systems1221.whereMyCake.service.ReportService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/reports")
@Tag(name = "Контроллер работы с отчетами")
@Slf4j
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(path = "/daily/total")
    public ResponseEntity<?> getDailyUserReport(@RequestParam UUID userId,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getDailyReport method called for User with id: {} and date: {}", userId, date);
        try {
            DailyReport report = reportService.getDailyUserReport(userId, date);
            return ResponseEntity.ok(report);
        } catch (IllegalArgumentException e) {
            log.error("Validation for report error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/daily/check")
    public ResponseEntity<?> checkDailyUserCalorie(@RequestParam UUID userId,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("checkDailyUserCalorie method called for User with id: {} and date: {}", userId, date);
        try {
            String check = reportService.checkDailyUserCalorie(userId, date);
            return ResponseEntity.ok(check);
        } catch (IllegalArgumentException e) {
            log.error("Validation for checking error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
