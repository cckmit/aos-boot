package ink.aos.boot.architecture.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Query<T extends Query<?, ?>, U> {

    long count();

    U singleResult();

    List<U> list();

    Page<U> listPage(Pageable pageable);

}
