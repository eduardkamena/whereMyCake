package ru.systems1221.whereMyCake.constant.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import ru.systems1221.whereMyCake.constant.GenderEnum;

import java.io.IOException;

/**
 * Десериализатор для преобразования JSON-значения в элемент перечисления {@link GenderEnum}.
 * Используется Jackson для автоматического преобразования строковых значений в соответствующие элементы перечисления.
 */
public class GenderEnumDeserializer extends JsonDeserializer<GenderEnum> {

    /**
     * Десериализует JSON-значение в элемент перечисления {@link GenderEnum}.
     *
     * @param p    JSON-парсер.
     * @param ctxt Контекст десериализации.
     * @return Элемент перечисления {@link GenderEnum}, соответствующий значению JSON.
     * @throws IOException Если возникает ошибка при чтении JSON.
     */
    @Override
    public GenderEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String genderValue = p.getText();
        return GenderEnum.fromGenderValue(genderValue);
    }
}
