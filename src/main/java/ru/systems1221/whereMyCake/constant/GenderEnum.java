package ru.systems1221.whereMyCake.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;

import ru.systems1221.whereMyCake.constant.deserializer.GenderEnumDeserializer;
import ru.systems1221.whereMyCake.exception.DoesNotEnumException;

import java.util.Arrays;

/**
 * Перечисление, представляющее пол пользователя (Мужской, Женский).
 * Каждый элемент перечисления имеет текстовое значение, которое используется для сериализации и десериализации.
 */
@Getter
@JsonDeserialize(using = GenderEnumDeserializer.class)
public enum GenderEnum {

    M("М"),
    F("Ж");

    private final String genderValue;

    GenderEnum(String genderValue) {
        this.genderValue = genderValue;
    }

    /**
     * Возвращает элемент перечисления {@link GenderEnum}, соответствующий переданному текстовому значению.
     *
     * @param genderValue Текстовое значение пола.
     * @return Элемент перечисления {@link GenderEnum}.
     * @throws DoesNotEnumException Если переданное значение не соответствует ни одному элементу перечисления.
     */
    public static GenderEnum fromGenderValue(String genderValue) {
        for (GenderEnum gender : GenderEnum.values()) {
            if (gender.getGenderValue().equals(genderValue)) {
                return gender;
            }
        }
        throw new DoesNotEnumException("Gender should be one of the values in GenderEnum: " + Arrays.toString(GenderEnum.values()));
    }
}
