package test;


import com.fasterxml.jackson.databind.ObjectMapper;
import ink.aos.boot.architecture.doc.DocGenerate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestA {

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Test
    public void test() {
        log.debug(objectMapper.writeValueAsString(DocGenerate.get("test")));
    }
}
