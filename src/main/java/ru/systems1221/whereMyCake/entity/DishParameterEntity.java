package ru.systems1221.whereMyCake.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Сущность, представляющая параметры блюда (калории, белки, жиры, углеводы).
 * Связана с сущностью {@link DishEntity}.
 */
@Entity(name = "dishes_parameters")
@Data
@NoArgsConstructor
@Schema(description = "Сущность параметров блюда")
public class DishParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    @Schema(description = "Идентификатор параметров блюда")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Schema(description = "Количество калорий на порцию блюда")
    @Column(name = "calorie", nullable = false)
    private float calorie;

    @Schema(description = "Количество белков на порцию блюда")
    @Column(name = "protein", nullable = false)
    private float protein;

    @Schema(description = "Количество жиров на порцию блюда")
    @Column(name = "fat", nullable = false)
    private float fat;

    @Schema(description = "Количество углеводов на порцию блюда")
    @Column(name = "carb", nullable = false)
    private float carb;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dishes_id", nullable = false)
    private DishEntity dishes;
}
