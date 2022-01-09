package ink.aos.boot.web.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.oas.configuration.OpenApiDocumentationConfiguration;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.nio.ByteBuffer;
import java.util.*;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Springfox OpenAPI configuration.
 * <p>
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue.
 * In that case, you can use the "no-api-docs" Spring profile, so that this bean is ignored.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({
        ApiInfo.class,
        BeanValidatorPluginsConfiguration.class,
        Docket.class
})
@Profile("swagger")
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({
        OpenApiDocumentationConfiguration.class,
        Swagger2DocumentationConfiguration.class,
        BeanValidatorPluginsConfiguration.class
})
public class SpringfoxAutoConfiguration {

    static final String MANAGEMENT_TITLE_SUFFIX = "Management API";
    static final String MANAGEMENT_GROUP_NAME = "management";
    static final String MANAGEMENT_DESCRIPTION = "Management endpoints documentation";

    private final Logger log = LoggerFactory.getLogger(SpringfoxAutoConfiguration.class);

    private final SwaggerProperties properties;

    public SpringfoxAutoConfiguration(SwaggerProperties properties) {
        this.properties = properties;
    }

    /**
     * Springfox configuration for the OpenAPI docs.
     *
     * @param springfoxCustomizers Springfox customizers
     * @param alternateTypeRules   alternate type rules
     * @return the Springfox configuration
     */
    @Bean
    @ConditionalOnMissingBean(name = "openAPISpringfoxApiDocket")
    public Docket openAPISpringfoxApiDocket(List<SpringfoxCustomizer> springfoxCustomizers,
                                            ObjectProvider<AlternateTypeRule[]> alternateTypeRules) {
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = createDocket();

        // Apply all OpenAPICustomizers orderly.
        springfoxCustomizers.forEach(customizer -> customizer.customize(docket));

        // Add all AlternateTypeRules if available in spring bean factory.
        // Also you can add your rules in a customizer bean above.
        Optional.ofNullable(alternateTypeRules.getIfAvailable()).ifPresent(docket::alternateTypeRules);

        watch.stop();
        return docket;
    }

    /**
     * JHipster Springfox Customizer
     *
     * @return the Sringfox Customizer of JHipster
     */
    @Bean
    public AosSpringfoxCustomizer aosSpringfoxCustomizer() {
        return new AosSpringfoxCustomizer(properties);
    }

    /**
     * Springfox configuration for the management endpoints (actuator) OpenAPI docs.
     *
     * @param appName the application name
     * @return the Springfox configuration
     */
    @Bean
    @ConditionalOnClass(name = "org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties")
    @ConditionalOnMissingBean(name = "openAPISpringfoxManagementDocket")
    public Docket openAPISpringfoxManagementDocket(@Value("${spring.application.name:application}") String appName) {

        ApiInfo apiInfo = new ApiInfo(
                StringUtils.capitalize(appName) + " " + MANAGEMENT_TITLE_SUFFIX,
                MANAGEMENT_DESCRIPTION,
                properties.getVersion(),
                "",
                ApiInfo.DEFAULT_CONTACT,
                "",
                "",
                new ArrayList<>()
        );

        Docket docket = createDocket();
//        for (JHipsterProperties.ApiDocs.Server server : properties.getServers()) {
//            docket.servers(new Server(server.getName(), server.getUrl(), server.getDescription(),
//                    Collections.emptyList(), Collections.emptyList()));
//        }

        docket = docket
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
                .groupName(MANAGEMENT_GROUP_NAME)
                .host(properties.getHost())
                .protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
                .forCodeGeneration(true)
                .directModelSubstitute(ByteBuffer.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class);

        // ignore Pageable parameter only if the class is present
        if (ClassUtils.isPresent("org.springframework.data.domain.Pageable", SpringfoxAutoConfiguration.class.getClassLoader())) {
            docket = docket.ignoredParameterTypes(org.springframework.data.domain.Pageable.class);
        }

        return docket
                .select()
                .paths(regex("/management/.*"))
                .build();
    }

    /**
     * <p>createDocket.</p>
     *
     * @return a {@link springfox.documentation.spring.web.plugins.Docket} object.
     */
    protected Docket createDocket() {
        return new Docket(DocumentationType.OAS_30);
    }

}
