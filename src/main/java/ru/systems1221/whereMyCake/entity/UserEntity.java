package ru.systems1221.whereMyCake.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systems1221.whereMyCake.constant.AimEnum;

import java.util.UUID;

@Entity(name = "users")
@Data
@NoArgsConstructor
@Schema(description = "Параметры пользователя")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Идентификатор пользователя")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Schema(description = "Имя пользователя")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "Email пользователя")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

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
    private AimEnum aim;
}
