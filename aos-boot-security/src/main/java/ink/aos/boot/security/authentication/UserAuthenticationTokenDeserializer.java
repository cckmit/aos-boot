package ink.aos.boot.security.authentication;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ink.aos.boot.security.User;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class UserAuthenticationTokenDeserializer extends JsonDeserializer<UserAuthenticationToken> {

    @Override
    public UserAuthenticationToken deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        User user = mapper.convertValue(jsonNode.get("user"), new TypeReference<User>() {
        });
        String accessToken = jsonNode.get("accessToken").asText();
        long expiresIn = jsonNode.get("expiresIn").asLong();

        UserAuthenticationToken token = new UserAuthenticationToken(user, accessToken, expiresIn);

        String rememberMeToken = jsonNode.has("rememberMeToken") ? jsonNode.get("rememberMeToken").asText() : null;
        if (StringUtils.isNotBlank(rememberMeToken)) {
            long rememberMeExpiresIn = jsonNode.get("rememberMeExpiresIn").asLong();
            token.setRememberMe(rememberMeToken, rememberMeExpiresIn);
        }
        return token;
    }

}
