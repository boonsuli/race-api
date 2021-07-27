package com.trailerplan.model.entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trailerplan.model.dto.RaceDTO;

@Data
public abstract class AbstractEntity<E extends RaceEntity, D extends RaceDTO> implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntity.class);

    public AbstractEntity() {}

    /** Doser mapper for the DTO extractor */
    @JsonIgnore
    @org.springframework.data.annotation.Transient
    protected DozerBeanMapper entityMapper = new DozerBeanMapper();

    /** Let the extended class implement the DTO extraction method */
    public abstract D extractDTO() throws InvocationTargetException, IllegalAccessException;

    public abstract E setPropertiesFromEntity(final E entity) throws InvocationTargetException, IllegalAccessException;

}
