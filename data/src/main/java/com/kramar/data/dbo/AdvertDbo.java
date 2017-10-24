package com.kramar.data.dbo;

import com.kramar.data.listener.AdvertEntityListener;
import com.kramar.data.listener.AuditableEntityListener;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.AdvertType;
import com.kramar.data.type.CurrencyType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "adverts")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvertDbo extends AbstractAuditableEntity {

    private static final long serialVersionUID = -68896789165924404L;

    @Column(name = "advert_status")
    @Enumerated(EnumType.STRING)
    private AdvertStatus advertStatus;

    @Column(name = "advert_type")
    @Enumerated(EnumType.STRING)
    private AdvertType advertType;

    @Column(name = "headline")
    private String headLine;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "currency_type")
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDbo owner;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "image2entity",
            joinColumns = @JoinColumn(name = "entity_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<ImageDbo> images;

}
