package ru.systems1221.whereMyCake.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import ru.systems1221.whereMyCake.constant.AimEnum;
import ru.systems1221.whereMyCake.exception.DoesNotEnumException;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class AimEnumDeserializer extends JsonDeserializer<AimEnum> {

    @Override
    public AimEnum deserialize(JsonParser p, DeserializationContext src) throws IOException {
        String value = p.getText().toUpperCase();
        for (AimEnum enumValue : AimEnum.values()) {
            if (enumValue.name().equals(value)) {
                return enumValue;
            }
        }
        log.error("Error query must be valid ENUM value");
        throw new DoesNotEnumException("Aim \"" + value + "\" should be one of the values in AimEnum: " + Arrays.toString(AimEnum.values()));
    }
}
