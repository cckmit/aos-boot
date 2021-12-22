package ink.aos.boot.architecture.doc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface DocAggregateRoot {

    String aggregate();

    String name();

}
