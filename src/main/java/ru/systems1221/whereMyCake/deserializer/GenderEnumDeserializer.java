package ru.systems1221.whereMyCake.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.systems1221.whereMyCake.constant.GenderEnum;

import java.io.IOException;

public class GenderEnumDeserializer extends JsonDeserializer<GenderEnum> {

    @Override
    public GenderEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String genderValue = p.getText();
        return GenderEnum.fromGenderValue(genderValue);
    }
}
