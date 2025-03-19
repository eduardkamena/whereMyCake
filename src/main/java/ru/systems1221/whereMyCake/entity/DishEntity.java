package ru.systems1221.whereMyCake.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Сущность, представляющая блюдо пользователя.
 * Содержит информацию о названии блюда, дате приема и параметрах (калории, белки, жиры, углеводы).
 * Связана с сущностью {@link UserEntity}.
 */
@Entity(name = "dishes")
@Data
@NoArgsConstructor
@Schema(description = "Сущность блюда пользователя")
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    @Schema(description = "Идентификатор блюда")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Schema(description = "Название блюда")
    @Column(name = "title", nullable = false)
    private String title;

    @JsonIgnore
    @Schema(description = "Дата приема блюда")
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @JsonIgnoreProperties(value = "dishes", allowSetters = true)
    @OneToMany(mappedBy = "dishes", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список параметров блюда")
    private List<DishParameterEntity> parameters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private UserEntity users;
}
