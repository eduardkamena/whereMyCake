package ru.systems1221.whereMyCake.controller;

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
@Tag(name = "Пользователи")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user) {
        log.info("createUser method called for User with id: {}", user.getId());
        UserEntity createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}
