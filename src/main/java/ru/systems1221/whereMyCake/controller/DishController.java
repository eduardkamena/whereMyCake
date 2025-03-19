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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import ru.systems1221.whereMyCake.entity.DishEntity;
import ru.systems1221.whereMyCake.exception.UserNotFoundException;
import ru.systems1221.whereMyCake.service.DishService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dishes")
@Tag(name = "Контроллер работы с блюдами",
        description = "API для работы с блюдами пользователя")
@Slf4j
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @Operation(
            summary = "Добавить список блюд для пользователя",
            description = "Добавляет список блюд для пользователя по его ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Блюда успешно добавлены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DishEntity.class),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": "123e4567-e89b-12d3-a456-426614174000",
                                        "title": "Салат Цезарь",
                                        "dateTime": "2025-03-18T12:00:00",
                                        "parameters": [
                                          {
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
                    description = "Некорректный запрос (например, неверные данные блюд)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "Validation error: Invalid input data")
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
    @PostMapping(path = "/{userId}")
    public ResponseEntity<?> addDishes(
            @Parameter(
                    description = "ID пользователя",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable UUID userId,

            @Parameter(
                    description = "Список блюд для добавления",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DishEntity.class),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "title": "Салат Цезарь",
                                        "parameters": [
                                          {
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
            )
            @RequestBody List<DishEntity> dishes
    ) {
        log.info("addDish method called with Dish id: {}", userId);
        try {
            List<DishEntity> addedDishes = dishService.addDishes(userId, dishes);
            return ResponseEntity.ok(addedDishes);
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UserNotFoundException e) {
            log.error("User not found with id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
