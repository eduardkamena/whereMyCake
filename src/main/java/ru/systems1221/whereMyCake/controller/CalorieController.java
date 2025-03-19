package ru.systems1221.whereMyCake.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.systems1221.whereMyCake.exception.UserNotFoundException;
import ru.systems1221.whereMyCake.service.CalorieService;

import java.util.UUID;

@RestController
@RequestMapping("/calorie")
@Tag(name = "Контроллер работы с дневной нормой калорий пользователя",
        description = "API для работы с дневной нормой калорий пользователя")
@Slf4j
@RequiredArgsConstructor
public class CalorieController {

    private final CalorieService calorieService;

    @Operation(
            summary = "Получить дневную норму калорий пользователя",
            description = "Возвращает дневную норму калорий для пользователя по его ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Дневная норма калорий успешно получена",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "1500.0")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос (например, userId равен null)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "Invalid input data: User id cannot be null")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "User not found with id: 123e4567-e89b-12d3-a456-426614174000")
                    )
            )
    })
    @GetMapping(path = "{userId}")
    public ResponseEntity<?> getUserCalorie(
            @Parameter(
                    description = "ID пользователя",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable UUID userId
    ) {
        log.info("getUserCalorie method called for User with id: {}", userId);
        if (userId == null) {
            log.warn("User id is null");
            return ResponseEntity.badRequest().build();
        }
        try {
            Float userCalorie = calorieService.getUserCalorie(userId);
            return ResponseEntity.ok(userCalorie);
        } catch (IllegalArgumentException e) {
            log.error("Invalid input data: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UserNotFoundException e) {
            log.error("User not found with id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
