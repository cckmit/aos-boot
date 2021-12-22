package ink.aos.boot.db.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ink.aos.boot.core.context.threadlocal.InvocationInfoProxy;
import ink.aos.boot.db.config.ApDBConstants;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Data
@MappedSuperclass
@FilterDef(name = ApDBConstants.TENANT_FILTER, parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = ApDBConstants.TENANT_FILTER, condition = "tenant_id = :tenantId")
public abstract class AbstractAuditingTenantEntity extends AbstractAuditingEntity {

    @JsonIgnore
    private String tenantId;

    @PrePersist
    @PreUpdate
    @PreRemove
    public void onPrePersist() {
        tenantId = InvocationInfoProxy.getTenantId();
    }
}
