package ink.aos.boot.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.MissingNode;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonDeserialize(using = UserGrantedAuthorityDeserializer.class)
public class UserGrantedAuthority implements GrantedAuthority {

    private String authority;

}

class UserGrantedAuthorityDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        String authority = readJsonNode(jsonNode, "authority").asText();
        return UserGrantedAuthority.builder().authority(authority).build();
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

}
