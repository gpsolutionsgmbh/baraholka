package com.kramar.data.listener;

import com.kramar.data.dbo.AbstractAuditableEntity;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Configurable
public class AuditableEntityListener {

    @PrePersist
    public void setCreatedTime(final AbstractAuditableEntity targetEntity) {
        targetEntity.setCreatedTime(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdatedTime(final AbstractAuditableEntity targetEntity) {
        targetEntity.setUpdatedTime(LocalDateTime.now());
    }

}
