package com.kramar.data.converter;


import com.kramar.data.dbo.AbstractEntity;
import com.kramar.data.dto.AbstractDto;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractDtoConverter<Entity extends AbstractEntity, Dto extends AbstractDto> {

    @PersistenceContext
    protected EntityManager em;

    public Dto transform(Entity entity) {
        Dto dto = createNewDto();
        doEntityToDtoTransform(entity, dto);
        return dto;
    }

    public Entity transform(Dto dto) {
        Entity entity = createEntity(dto);
        doDtoToEntityTransform(dto, entity);
        return entity;
    }

    private void doDtoToEntityTransform(Dto dto, Entity entity) {
        if (dto == null) return;
        updateEntitySystemFields(dto, entity);
        convertFromDto(dto, entity);
    }

    private void updateEntitySystemFields(Dto dto, Entity entity) {
        if (Objects.nonNull(dto) && Objects.nonNull(entity)) {
            entity.setId(dto.getId());
        }
    }

    private Entity createEntity(Dto dto) {
        Entity entity = createNewEntity();
        if (dto.getId() != null) {
            entity = (Entity) em.find(entity.getClass(), dto.getId());
        }
        return entity;
    }

    private void doEntityToDtoTransform(Entity entity, Dto dto) {
        if (entity == null) return;
        updateDtoSystemFields(entity, dto);
        convertFromEntity(entity, dto);
    }

    private void updateDtoSystemFields(Entity entity, Dto dto) {
        if (Objects.nonNull(entity) && Objects.nonNull(dto)) {
            dto.setId(entity.getId());
        }
    }

    abstract Dto createNewDto();

    abstract Entity createNewEntity();

    abstract void convertFromEntity(Entity entity, Dto dto);

    abstract void convertFromDto(Dto dto, Entity entity);

}
