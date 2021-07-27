package com.trailerplan.model.common;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class ItraPointConverter implements AttributeConverter<ItraPointEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ItraPointEnum itraPointEnum) { return itraPointEnum.getItraPoint(); }

    @Override
    public ItraPointEnum convertToEntityAttribute(Integer point) { return ItraPointEnum.valueOf(point); }
}
