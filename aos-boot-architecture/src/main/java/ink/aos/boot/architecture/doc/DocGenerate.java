package ink.aos.boot.architecture.doc;

import lombok.SneakyThrows;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.util.*;

public class DocGenerate {

    private static ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    private static MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver.getClassLoader());

    @SneakyThrows
    public static Doc get(String packageName) {
        Doc doc = new Doc();
        Map<String, Set<Class<?>>> classes = new HashMap<>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                .concat(ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(packageName))
                        .concat("/**/*.class"));
        Resource[] resources = resolver.getResources(packageSearchPath);
        MetadataReader metadataReader = null;
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                metadataReader = metadataReaderFactory.getMetadataReader(resource);
                try {
                    if (metadataReader.getClassMetadata().isConcrete()) {
                        MergedAnnotations annotations = metadataReader.getAnnotationMetadata().getAnnotations();
                        final String _className = metadataReader.getClassMetadata().getClassName();
                        final String _packageName = Class.forName(_className).getPackage().getName();

                        classes.put(_packageName, new TreeSet<>());

                        doc.getAggregates().put(_packageName, new Doc.Aggregate());

                        annotations.forEach(annotationMergedAnnotation -> {
                            if (annotationMergedAnnotation.getType().equals(DocAggregateRoot.class)) {
                                DocAggregateRoot aggregateRoot = (DocAggregateRoot) annotationMergedAnnotation.getSource();
                                aggregateRoot.name();
                                aggregateRoot.aggregate();
                                Doc.Aggregate aggregate = doc.getAggregates().get(_packageName);
                                Doc.AggregateRoot root = new Doc.AggregateRoot();
                                root.setClassName(_className);
                                root.setName(aggregateRoot.name());
                                aggregate.setAggregateRoot(root);
                                aggregate.setName(aggregateRoot.aggregate());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return doc;
    }

}
