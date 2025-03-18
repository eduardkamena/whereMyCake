package ru.systems1221.whereMyCake.controller;

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
@Tag(name = "Дневная норма калорий")
@Slf4j
@RequiredArgsConstructor
public class CalorieController {

    private final CalorieService calorieService;

    @GetMapping(path = "{id}")
    public ResponseEntity<Float> getUserCalorie(@PathVariable UUID id) {
        log.info("getUserCalorie method called for User with id: {}", id);
        if (id == null) {
            log.warn("User id is null");
            return ResponseEntity.badRequest().build();
        }
        try {
            Float userCalorie = calorieService.getUserCalorie(id);
            return ResponseEntity.ok(userCalorie);
        } catch (IllegalArgumentException e) {
            log.error("Invalid input data: {}", id);
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException e) {
            log.error("User not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
