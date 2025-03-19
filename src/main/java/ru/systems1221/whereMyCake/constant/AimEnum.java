package ru.systems1221.whereMyCake.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;

import ru.systems1221.whereMyCake.constant.deserializer.AimEnumDeserializer;
import ru.systems1221.whereMyCake.exception.DoesNotEnumException;

import java.util.Arrays;

/**
 * Перечисление, представляющее цели пользователя (похудение, поддержание веса, набор массы).
 * Каждый элемент перечисления имеет текстовое значение, которое используется для сериализации и десериализации.
 */
@Getter
@JsonDeserialize(using = AimEnumDeserializer.class)
public enum AimEnum {

    WEIGHT_LOSS("Похудение"),
    WEIGHT_MAINTENANCE("Поддержание"),
    WEIGHT_GAIN("Набор массы");

    private final String aimValue;

    AimEnum(String aimValue) {
        this.aimValue = aimValue;
    }

    /**
     * Возвращает элемент перечисления {@link AimEnum}, соответствующий переданному текстовому значению.
     *
     * @param aimValue Текстовое значение цели.
     * @return Элемент перечисления {@link AimEnum}.
     * @throws DoesNotEnumException Если переданное значение не соответствует ни одному элементу перечисления.
     */
    public static AimEnum fromAimValue(String aimValue) {
        for (AimEnum aim : AimEnum.values()) {
            if (aim.getAimValue().equals(aimValue)) {
                return aim;
            }
        }
        throw new DoesNotEnumException("Aim should be one of the values in AimEnum: " + Arrays.toString(AimEnum.values()));
    }
}
