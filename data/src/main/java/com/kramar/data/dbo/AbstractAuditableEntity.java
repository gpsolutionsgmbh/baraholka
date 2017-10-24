package com.kramar.data.dbo;

import com.kramar.data.dbo.AbstractEntity;
import com.kramar.data.listener.AdvertEntityListener;
import com.kramar.data.listener.AuditableEntityListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners({AuditableEntityListener.class, AdvertEntityListener.class})
@Data
@EqualsAndHashCode(callSuper = true, of = {"version"})
@Access(AccessType.FIELD)
public abstract class AbstractAuditableEntity extends AbstractEntity {

    private static final long serialVersionUID = 4264565815894959098L;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @CreatedBy
    @Column(name = "created_by")
    private UUID createdBy;

    @LastModifiedDate
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @LastModifiedBy
    @Column(name = "updated_by")
    private UUID updatedBy;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "integer default 0")
    protected Long version;

}
