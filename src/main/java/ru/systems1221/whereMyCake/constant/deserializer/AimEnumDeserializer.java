package ru.systems1221.whereMyCake.constant.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import ru.systems1221.whereMyCake.constant.AimEnum;

import java.io.IOException;

/**
 * Десериализатор для преобразования JSON-значения в элемент перечисления {@link AimEnum}.
 * Используется Jackson для автоматического преобразования строковых значений в соответствующие элементы перечисления.
 */
public class AimEnumDeserializer extends JsonDeserializer<AimEnum> {

    /**
     * Десериализует JSON-значение в элемент перечисления {@link AimEnum}.
     *
     * @param p    JSON-парсер.
     * @param ctxt Контекст десериализации.
     * @return Элемент перечисления {@link AimEnum}, соответствующий значению JSON.
     * @throws IOException Если возникает ошибка при чтении JSON.
     */
    @Override
    public AimEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String aimValue = p.getText();
        return AimEnum.fromAimValue(aimValue);
    }
}
