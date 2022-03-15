package ink.aos.boot.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDeserializer extends JsonDeserializer {

    private static final TypeReference<List<UserGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_SET = new TypeReference<List<UserGrantedAuthority>>() {
    };

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        List<UserGrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"),
                SIMPLE_GRANTED_AUTHORITY_SET);
        JsonNode passwordNode = readJsonNode(jsonNode, "password");
        String id = readJsonNode(jsonNode, "id").asText();
        String username = readJsonNode(jsonNode, "username").asText();
        String tenantId = readJsonNode(jsonNode, "tenantId").asText();
        String name = readJsonNode(jsonNode, "name").asText();
        String mobile = readJsonNode(jsonNode, "mobile").asText();
        String email = readJsonNode(jsonNode, "email").asText();
        User result = User.builder()
                .id(id)
                .username(username)
                .tenantId(tenantId)
                .name(name)
                .mobile(mobile)
                .email(email)
                .authorities(authorities)
                .uaaTypeCodes(readMap(jsonNode, "uaaTypeCodes"))
                .build();
        if (passwordNode.asText(null) == null) {
            result.eraseCredentials();
        }
        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

    private Map<String, String> readMap(JsonNode jsonNode, String field) {
        Map<String, String> m = new HashMap<>();
        JsonNode node = jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
        node.fields().forEachRemaining(stringJsonNodeEntry -> m.put(stringJsonNodeEntry.getKey(), stringJsonNodeEntry.getValue().asText()));
        return m;
    }

}
