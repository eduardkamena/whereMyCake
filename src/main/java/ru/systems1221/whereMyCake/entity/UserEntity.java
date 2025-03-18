package ru.systems1221.whereMyCake.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systems1221.whereMyCake.constant.AimEnum;
import ru.systems1221.whereMyCake.constant.GenderEnum;

import java.util.UUID;

@Entity(name = "users")
@Data
@NoArgsConstructor
@Schema(description = "Сущность пользователя")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
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
    @Column(name = "gender", columnDefinition = "VARCHAR(1)", nullable = false)
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
    @Column(name = "aim", columnDefinition = "VARCHAR(12)", nullable = false)
    @Enumerated(EnumType.STRING)
    private AimEnum aim;
}
