package com.kramar.data.dbo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Data
@EqualsAndHashCode
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 8874971979191857414L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Access(AccessType.PROPERTY)
    private UUID id;

}
