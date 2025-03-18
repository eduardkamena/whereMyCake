package ru.systems1221.whereMyCake.controller;

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
@Tag(name = "Контроллер работы с блюдами")
@Slf4j
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @PostMapping(path = "/{userId}")
    public ResponseEntity<?> addDishes(@PathVariable UUID userId, @RequestBody List<DishEntity> dishes) {

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
