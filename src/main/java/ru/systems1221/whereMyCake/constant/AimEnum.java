package ru.systems1221.whereMyCake.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.systems1221.whereMyCake.deserializer.AimEnumDeserializer;

//@JsonDeserialize(using = AimEnumDeserializer.class)
public enum AimEnum {

    WEIGHT_LOSS("Похудение"),
    WEIGHT_MAINTENANCE("Поддержание"),
    WEIGHT_GAIN("Набор массы");

    private final String aimValue;

    AimEnum(String aimValue) {
        this.aimValue = aimValue;
    }
}
