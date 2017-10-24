package com.kramar.data.dbo;

import com.kramar.data.listener.AdvertEntityListener;
import com.kramar.data.listener.AuditableEntityListener;
import com.kramar.data.type.AdvertHistoryStatus;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.AdvertType;
import com.kramar.data.type.CurrencyType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "advert_history")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@EntityListeners({AdvertEntityListener.class})
public class AdvertHistoryDbo extends AbstractEntity {

    private static final long serialVersionUID = 5038406325659857106L;

    @Column(name = "advert_history_status")
    @Enumerated(EnumType.STRING)
    private AdvertHistoryStatus advertHistoryStatus;

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

    @Column(name = "user_id")
    private UUID owner;

    @Column(name = "headline_image_id")
    private UUID headLineImage;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Column(name = "updated_by")
    private UUID updatedBy;
}
