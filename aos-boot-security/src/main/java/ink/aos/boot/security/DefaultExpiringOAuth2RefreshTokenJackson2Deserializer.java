package ink.aos.boot.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.io.IOException;
import java.util.Date;

public class DefaultExpiringOAuth2RefreshTokenJackson2Deserializer extends StdDeserializer<OAuth2RefreshToken> {

    protected DefaultExpiringOAuth2RefreshTokenJackson2Deserializer(Class<?> vc) {
        super(vc);
    }

    private static <T> T readValue(ObjectMapper mapper, JsonNode jsonNode, Class<T> clazz) {
        if (mapper == null || jsonNode == null || clazz == null) {
            return null;
        }
        try {
            return mapper.readValue(jsonNode.traverse(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OAuth2RefreshToken deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        String value = readValue(mapper, jsonNode.get("value"), String.class);
        Date expiration = readValue(mapper, jsonNode.get("expiration"), Date.class);
        return new DefaultExpiringOAuth2RefreshToken(value, expiration);
    }
}