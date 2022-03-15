package ink.aos.boot.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDeserializer extends JsonDeserializer {

    private static final TypeReference<List<UserGrantedAuthority>> SIMPLE_GRANTED_AUTHORITY_SET = new TypeReference<List<UserGrantedAuthority>>() {
    };

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
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
                .uaaTypeCodes(readArray(jsonNode, "uaaTypeCodes"))
                .build();
        if (passwordNode.asText(null) == null) {
            result.eraseCredentials();
        }
        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

    private List<String> readArray(JsonNode jsonNode, String field) {
        List<String> s = new ArrayList<>();
        JsonNode node = jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
        if (node.isArray()) {
            Iterator<JsonNode> it = node.iterator();
            while (it.hasNext()) {
                JsonNode n = it.next();
                s.add(n.asText());
            }
        }
        return s;
    }

}
