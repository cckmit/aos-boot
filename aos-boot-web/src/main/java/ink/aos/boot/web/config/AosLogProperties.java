package ink.aos.boot.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "aos.log")
public class AosLogProperties {

    private boolean useJsonFormat = false;
    private final Logstash logstash = new Logstash();
    private final File file = new File();

    @Getter
    @Setter
    public static class Logstash {
        private boolean enabled = false;
        private String host = "localhost";
        private int port = 5000;
        private int queueSize = 512;
    }

    @Getter
    @Setter
    public static class File {
        private boolean enabled = true;
    }

}
