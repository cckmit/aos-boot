package ink.aos.boot.security.authentication;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class UserAuthenticationTokenSerializer extends JsonSerializer<UserAuthenticationToken> {

    @Override
    public void serialize(UserAuthenticationToken userAuthenticationToken, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField("user", userAuthenticationToken.getUser());
        jgen.writeStringField("access_token", userAuthenticationToken.getAccessToken());
        jgen.writeNumberField("expires_in", userAuthenticationToken.getExpiresIn());
        if (StringUtils.isNotBlank(userAuthenticationToken.getRememberMeToken())) {
            jgen.writeStringField("remember_me_token", userAuthenticationToken.getRememberMeToken());
            jgen.writeNumberField("remember_me_expires_in", userAuthenticationToken.getRememberMeExpiresIn());
        }
        jgen.writeEndObject();
    }

    @Override
    public void serializeWithType(UserAuthenticationToken value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.VALUE_STRING));
        serialize(value, gen, serializers);
        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}
