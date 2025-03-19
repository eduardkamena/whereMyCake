package ru.systems1221.whereMyCake.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;

import ru.systems1221.whereMyCake.constant.deserializer.GenderEnumDeserializer;
import ru.systems1221.whereMyCake.exception.DoesNotEnumException;

import java.util.Arrays;

@Getter
@JsonDeserialize(using = GenderEnumDeserializer.class)
public enum GenderEnum {

    M("М"),
    F("Ж");

    private final String genderValue;

    GenderEnum(String genderValue) {
        this.genderValue = genderValue;
    }

    public static GenderEnum fromGenderValue(String genderValue) {
        for (GenderEnum gender : GenderEnum.values()) {
            if (gender.getGenderValue().equals(genderValue)) {
                return gender;
            }
        }
        throw new DoesNotEnumException("Gender should be one of the values in GenderEnum: " + Arrays.toString(GenderEnum.values()));
    }
}
