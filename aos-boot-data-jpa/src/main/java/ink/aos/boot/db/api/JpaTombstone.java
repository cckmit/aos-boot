package ink.aos.boot.db.api;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaTombstone<T extends AbstractAuditingEntity, PK> {

    @Modifying
    @Query(value = "update #{#entityName} set deleted = true where #{#id} = :id")
    void tombstone(@Param("id") PK id);

}
