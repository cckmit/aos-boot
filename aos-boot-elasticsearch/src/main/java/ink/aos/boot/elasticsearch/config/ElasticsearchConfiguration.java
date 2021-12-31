package ink.aos.boot.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;

@Configuration
@EnableConfigurationProperties({ElasticsearchRestClientProperties.class})
@EnableElasticsearchRepositories(value = "ink.aos", includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ElasticsearchRepository.class)})
public class ElasticsearchConfiguration {

//    @Bean
//    public EntityMapper getEntityMapper() {
//        return new CustomEntityMapper(mapper);
//    }

//    @Bean
//    SimpleElasticsearchMappingContext mappingContext() {
//        return new CustomElasticsearchMappingContext();
//    }
//
//    @Bean
//    @Primary
//    public ElasticsearchOperations elasticsearchTemplate(final RestHighLevelClient restClient,
//                                                         final SimpleElasticsearchMappingContext mappingContext) {
//        return new ElasticsearchRestTemplate(restClient, new MappingElasticsearchConverter(new CustomElasticsearchMappingContext()));
//    }

//    @Bean
//    @Primary
//    public ElasticsearchOperations elasticsearchTemplate(
//            JestClient jestClient,
//            ElasticsearchConverter elasticsearchConverter,
//            SimpleElasticsearchMappingContext mappingContext,
//            EntityMapper entityMapper
//    ) {
//        return new JestElasticsearchTemplate(
//                jestClient,
//                elasticsearchConverter,
//                new DefaultJestResultsMapper(mappingContext, entityMapper)
//        );
//    }

//    public class CustomEntityMapper implements EntityMapper {
//        private ObjectMapper objectMapper;
//
//        public CustomEntityMapper(ObjectMapper objectMapper) {
//            this.objectMapper = objectMapper;
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//            objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
//            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
//            objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
//        }
//
//        @Override
//        public String mapToString(Object object) throws IOException {
//            return objectMapper.writeValueAsString(object);
//        }
//
//        @Override
//        public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
//            return objectMapper.readValue(source, clazz);
//        }
//
//        @Override
//        public Map<String, Object> mapObject(Object source) {
//            try {
//                return objectMapper.readValue(mapToString(source), HashMap.class);
//            } catch (IOException e) {
//                throw new MappingException(e.getMessage(), e);
//            }
//        }
//
//        @Override
//        public <T> T readObject(Map<String, Object> source, Class<T> targetType) {
//            try {
//                return mapToObject(mapToString(source), targetType);
//            } catch (IOException e) {
//                throw new MappingException(e.getMessage(), e);
//            }
//        }
//    }
}

///**
// * Custom mapping context in order to use the same entities for both MongoDB and Elasticsearch datasources
// */
//class CustomElasticsearchMappingContext extends SimpleElasticsearchMappingContext {
//
//    @Override
//    protected ElasticsearchPersistentProperty createPersistentProperty(
//            Property property,
//            SimpleElasticsearchPersistentEntity owner,
//            SimpleTypeHolder simpleTypeHolder
//    ) {
//        return new CustomElasticsearchPersistentProperty(property, owner, simpleTypeHolder);
//    }
//}
//
//class CustomElasticsearchPersistentProperty extends SimpleElasticsearchPersistentProperty {
//
//    @SuppressWarnings({"unchecked"})
//    public CustomElasticsearchPersistentProperty(Property property, PersistentEntity owner, SimpleTypeHolder simpleTypeHolder) {
//        super(property, owner, simpleTypeHolder);
//    }
//
//    @Override
//    public boolean isAssociation() {
//        return false;
//    }
//}