package com.trailerplan.model.dto;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.trailerplan.model.entity.AbstractEntity;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractDTO<E extends AbstractEntity> implements Serializable {

    /** Jackson object mapper */
    @JsonIgnore
    @org.springframework.data.annotation.Transient
    protected ObjectMapper objectMapper = new ObjectMapper();

    /** Doser mapper for the entity extractor */
    @JsonIgnore
    @org.springframework.data.annotation.Transient
    protected Mapper dtoMapper = new DozerBeanMapper();

    public String toJson() throws IOException {
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(this);
    }

    /** Let the extended class implement the Entity extraction method */
    public abstract E extractEntity();

    @JsonIgnore
    public abstract Class<E> getEntityClass();
}
