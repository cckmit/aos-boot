package ink.aos.boot.db.api;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaTombstone<T extends AbstractAuditingEntity, ID> {

    @Modifying
    @Query(value = "update #{#entityName} set deleted = true where id = ?1")
    void tombstone(ID id);

}
