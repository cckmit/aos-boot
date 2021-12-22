package ink.aos.boot.architecture.doc;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Doc {

    private String BoundaryContext;
    private Map<String, Aggregate> aggregates = new HashMap<>();

    @Getter
    @Setter
    public static class Aggregate {

        private String name;

        private AggregateRoot aggregateRoot;

        private List<Entity> entities = new ArrayList<>();

        private List<ValueObject> valueObjects = new ArrayList<>();

    }

    @Getter
    @Setter
    public static class AggregateRoot extends Entity {
    }

    @Getter
    @Setter
    public static class Entity extends ValueObject {

    }

    @Getter
    @Setter
    public static class ValueObject {
        private String name;
        private String className;
        private List<Attribute> attributes = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class Attribute {
        private String name;
        private AttributeType type;
        private String className;
    }

}
