package ru.systems1221.whereMyCake.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.systems1221.whereMyCake.entity.UserEntity;
import ru.systems1221.whereMyCake.service.UserService;

@RestController
@RequestMapping("/users")
@Tag(name = "Контроллер работы с пользователями",
        description = "API для работы с пользователями")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Создать нового пользователя",
            description = "Создает нового пользователя с указанными данными."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно создан",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": "123e4567-e89b-12d3-a456-426614174000",
                                      "name": "Иван Вкомандухотелов",
                                      "email": "ivan@mail.ru",
                                      "gender": "М",
                                      "age": 25,
                                      "weight": 70,
                                      "height": 180,
                                      "aim": "Похудение"
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
                            examples = @ExampleObject(value = "Validation error: Invalid input data")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<?> createUser(
            @Parameter(
                    description = "Данные пользователя",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "name": "Иван Вкомандухотелов",
                                      "email": "ivan@mail.ru",
                                      "gender": "М",
                                      "age": 25,
                                      "weight": 70,
                                      "height": 180,
                                      "aim": "Похудение"
                                    }
                                    """
                            )
                    )
            )
            @Valid @RequestBody UserEntity user
    ) {
        log.info("createUser method called for User with id: {}", user.getId());
        try {
            UserEntity createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
