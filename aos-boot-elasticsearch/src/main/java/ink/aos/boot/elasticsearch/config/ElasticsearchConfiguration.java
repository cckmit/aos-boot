package ink.aos.boot.elasticsearch.config;

//@Configuration
public class ElasticsearchConfiguration  {
//
//    private final ElasticsearchRestClientProperties properties;
//
//    public ElasticsearchConfiguration(ElasticsearchRestClientProperties properties) {
//        this.properties = properties;
//    }
//
//    @Bean
//    @Override
//    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
//        return new ElasticsearchCustomConversions(
//                Arrays.asList(
//                        new ZonedDateTimeWritingConverter(),
//                        new ZonedDateTimeReadingConverter(),
//                        new InstantWritingConverter(),
//                        new InstantReadingConverter(),
//                        new LocalDateWritingConverter(),
//                        new LocalDateReadingConverter()
//                )
//        );
//    }
//
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(properties.getUris().toArray(new String[0]))
//                .withBasicAuth(properties.getUsername(), properties.getPassword())
//                .build();
//        return RestClients.create(clientConfiguration).rest();
//    }
//
//    @WritingConverter
//    static class ZonedDateTimeWritingConverter implements Converter<ZonedDateTime, String> {
//
//        @Override
//        public String convert(ZonedDateTime source) {
//            if (source == null) {
//                return null;
//            }
//            return source.toInstant().toString();
//        }
//    }
//
//    @ReadingConverter
//    static class ZonedDateTimeReadingConverter implements Converter<String, ZonedDateTime> {
//
//        @Override
//        public ZonedDateTime convert(String source) {
//            if (source == null) {
//                return null;
//            }
//            return Instant.parse(source).atZone(ZoneId.systemDefault());
//        }
//    }
//
//    @WritingConverter
//    static class InstantWritingConverter implements Converter<Instant, String> {
//
//        @Override
//        public String convert(Instant source) {
//            if (source == null) {
//                return null;
//            }
//            return source.toString();
//        }
//    }
//
//    @ReadingConverter
//    static class InstantReadingConverter implements Converter<String, Instant> {
//
//        @Override
//        public Instant convert(String source) {
//            if (source == null) {
//                return null;
//            }
//            return Instant.parse(source);
//        }
//    }
//
//    @WritingConverter
//    static class LocalDateWritingConverter implements Converter<LocalDate, String> {
//
//        @Override
//        public String convert(LocalDate source) {
//            if (source == null) {
//                return null;
//            }
//            return source.toString();
//        }
//    }
//
//    @ReadingConverter
//    static class LocalDateReadingConverter implements Converter<String, LocalDate> {
//
//        @Override
//        public LocalDate convert(String source) {
//            if (source == null) {
//                return null;
//            }
//            return LocalDate.parse(source);
//        }
//    }
}
