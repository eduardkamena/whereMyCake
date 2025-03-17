package ru.systems1221.whereMyCake.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import ru.systems1221.whereMyCake.constant.AimEnum;

import java.io.IOException;

@Slf4j
public class AimEnumDeserializer extends JsonDeserializer<AimEnum> {

    @Override
    public AimEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String aimValue = p.getText();
        return AimEnum.fromAimValue(aimValue);
    }
}
