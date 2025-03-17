package ru.systems1221.whereMyCake.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import ru.systems1221.whereMyCake.deserializer.AimEnumDeserializer;
import ru.systems1221.whereMyCake.exception.DoesNotEnumException;

import java.util.Arrays;

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

    public static AimEnum fromAimValue(String aimValue) {
        for (AimEnum aim : AimEnum.values()) {
            if (aim.getAimValue().equals(aimValue)) {
                return aim;
            }
        }
        throw new DoesNotEnumException("Aim should be one of the values in AimEnum: " + Arrays.toString(AimEnum.values()));
    }
}
