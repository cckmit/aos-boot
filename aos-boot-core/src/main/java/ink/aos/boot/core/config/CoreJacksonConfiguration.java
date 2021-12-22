package ink.aos.boot.core.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import ink.aos.boot.core.util.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class CoreJacksonConfiguration {

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String formatValue;

    public CoreJacksonConfiguration(JacksonProperties jacksonProperties) {
        if (StringUtils.isNotBlank(jacksonProperties.getDateFormat())) {
            formatValue = jacksonProperties.getDateFormat();
        } else {
            formatValue = DATE_FORMAT;
        }
    }

    @Bean(name = "format")
    public DateTimeFormatter format() {
        return DateTimeFormatter.ofPattern(formatValue);
    }

    @Bean
    public JavaTimeModule javaTimeModule(@Qualifier("format") DateTimeFormatter format) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(format));
        javaTimeModule.addSerializer(Instant.class, new InstantCustomSerializer(format));
        javaTimeModule.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat(formatValue)));
        javaTimeModule.addDeserializer(Instant.class, new InstantCustomDeserializer());
        javaTimeModule.addDeserializer(Date.class, new DateCustomDeserializer());
        return javaTimeModule;
    }

    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /*
     * Jackson Afterburner module to speed up serialization/deserialization.
     */
    @Bean
    public AfterburnerModule afterburnerModule() {
        return new AfterburnerModule();
    }


    class InstantCustomSerializer extends JsonSerializer<Instant> {
        private DateTimeFormatter format;

        private InstantCustomSerializer(DateTimeFormatter formatter) {
            this.format = formatter;
        }

        @Override
        public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (instant == null) {
                return;
            }
            String jsonValue = format.format(instant.atZone(ZoneId.systemDefault()));
            jsonGenerator.writeString(jsonValue);
        }
    }

    class InstantCustomDeserializer extends JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException {
            String dateString = p.getText().trim();
            if (StringUtils.isNotBlank(dateString)) {
                Date pareDate;
                try {
                    pareDate = DateFormatUtil.pareDate(dateString);
                    if (null != pareDate) {
                        return pareDate.toInstant();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }


    class DateCustomDeserializer extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException {
            String dateString = p.getText().trim();
            if (StringUtils.isNotBlank(dateString)) {
                try {
                    return DateFormatUtil.pareDate(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }


}
