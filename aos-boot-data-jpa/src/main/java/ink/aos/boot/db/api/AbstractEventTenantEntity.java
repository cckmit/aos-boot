package ink.aos.boot.db.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ink.aos.boot.core.context.threadlocal.InvocationInfoProxy;
import ink.aos.boot.db.config.ApDBConstants;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Audited
@EntityListeners({AuditingEntityListener.class})
@Data
@FilterDef(name = ApDBConstants.TENANT_FILTER, parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = ApDBConstants.TENANT_FILTER, condition = "tenant_id = :tenantId")
public abstract class AbstractEventTenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @JsonIgnore
    private String tenantId;

    @PrePersist
    @PreUpdate
    @PreRemove
    public void onPrePersist() {
        tenantId = InvocationInfoProxy.getTenantId();
    }
}
