package ru.systems1221.whereMyCake.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

import ru.systems1221.whereMyCake.constant.AimEnum;
import ru.systems1221.whereMyCake.constant.GenderEnum;

import java.util.UUID;

/**
 * Сущность, представляющая пользователя.
 * Содержит информацию о пользователе, такую как имя, email, пол, возраст, вес, рост и цель.
 */
@Entity(name = "users")
@Data
@NoArgsConstructor
@Schema(description = "Сущность пользователя")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //TODO @JsonIgnore возможно восстановить аннотацию после прохождения тестов в коллекции postman
    @Schema(description = "Идентификатор пользователя")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Schema(description = "Имя пользователя")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "Email пользователя")
    @Column(name = "email", unique = true, nullable = false)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email должен быть корректного формата")
    private String email;

    @Schema(description = "Пол пользователя")
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Schema(description = "Возраст пользователя")
    @Column(name = "age", nullable = false)
    private int age;

    @Schema(description = "Вес пользователя")
    @Column(name = "weight", nullable = false)
    private float weight;

    @Schema(description = "Рост пользователя")
    @Column(name = "height", nullable = false)
    private int height;

    @Schema(description = "Цель пользователя")
    @Column(name = "aim", nullable = false)
    @Enumerated(EnumType.STRING)
    private AimEnum aim;
}
