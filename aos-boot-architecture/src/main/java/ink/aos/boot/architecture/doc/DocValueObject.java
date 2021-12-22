package ink.aos.boot.architecture.doc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface DocValueObject {

    String name();

}
