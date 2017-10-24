package com.kramar.data.listener;

import com.kramar.data.dbo.AbstractAuditableEntity;
import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.AdvertHistoryDbo;
import com.kramar.data.dbo.ImageDbo;
import com.kramar.data.repository.AdvertHistoryRepository;
import com.kramar.data.type.AdvertHistoryStatus;
import com.kramar.data.type.ImageType;
import com.kramar.data.util.AutowireHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Configurable
public class AdvertEntityListener {

    @Autowired
    private AdvertHistoryRepository advertHistoryRepository;

    private void init() {
        if (advertHistoryRepository == null) {
            AutowireHelper.autowire(this, advertHistoryRepository);
        }
    }

// With TransactionSynchronizationManager, you have static methods to get information about current transaction,
// and you can register a TransactionSynchronization which allows you to automatically do a post-commit

    @PrePersist
    public void postPersist(final AbstractAuditableEntity targetEntity) {
        if (targetEntity instanceof AdvertDbo) {
            init();
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    AdvertHistoryDbo advertHistoryDbo = createAdvertHistoryEntity(targetEntity);
                    advertHistoryDbo.setAdvertHistoryStatus(AdvertHistoryStatus.ADDED);
                    advertHistoryRepository.saveAndFlush(advertHistoryDbo);
                }
            });
        }
    }

    @PreUpdate
    public void postUpdate(final AbstractAuditableEntity targetEntity) {
        if (targetEntity instanceof AdvertDbo) {
            init();
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    AdvertHistoryDbo advertHistoryDbo = createAdvertHistoryEntity(targetEntity);
                    advertHistoryDbo.setAdvertHistoryStatus(AdvertHistoryStatus.UPDATED);
                    advertHistoryRepository.save(advertHistoryDbo);
                }
            });
        }
    }

    @PreRemove
    public void postRemove(final AbstractAuditableEntity targetEntity) {
        if (targetEntity instanceof AdvertDbo) {
            init();
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    AdvertHistoryDbo advertHistoryDbo = createAdvertHistoryEntity(targetEntity);
                    advertHistoryDbo.setAdvertHistoryStatus(AdvertHistoryStatus.DELETED);
                    advertHistoryRepository.save(advertHistoryDbo);
                }
            });
        }
    }

    private AdvertHistoryDbo createAdvertHistoryEntity(final AbstractAuditableEntity targetEntity) {
        final AdvertDbo advertDbo = (AdvertDbo) targetEntity;
        AdvertHistoryDbo advertHistoryDbo = new AdvertHistoryDbo();
        advertHistoryDbo.setAdvertType(advertDbo.getAdvertType());
        advertHistoryDbo.setAdvertStatus(advertDbo.getAdvertStatus());
        advertHistoryDbo.setHeadLine(advertDbo.getHeadLine());
        advertHistoryDbo.setPrice(advertDbo.getPrice());
        advertHistoryDbo.setCurrencyType(advertDbo.getCurrencyType());
        advertHistoryDbo.setDescription(advertDbo.getDescription());
        advertHistoryDbo.setOwner(advertDbo.getOwner() != null ? advertDbo.getOwner().getId() : null);
        if (!CollectionUtils.isEmpty(advertDbo.getImages())) {
            advertHistoryDbo.setHeadLineImage(
                    advertDbo
                            .getImages()
                            .stream()
                            .filter(imageDbo -> imageDbo.getImageType().equals(ImageType.MAIN_IN_ADVERT))
                            .map(ImageDbo::getId)
                            .findFirst()
                            .orElse(null));
        }
        advertHistoryDbo.setCreatedBy(advertDbo.getCreatedBy());
        advertHistoryDbo.setCreatedTime(advertDbo.getCreatedTime());
        advertHistoryDbo.setUpdatedBy(advertDbo.getUpdatedBy());
        advertHistoryDbo.setUpdatedTime(advertDbo.getUpdatedTime());
        return advertHistoryDbo;
    }
}

