package com.trailerplan.model.converters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

@Slf4j
public class LocalDateToDateConverter implements CustomConverter {

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue,
                          Class<?> destinationClass, Class<?> sourceClass) {
        log.debug(existingDestinationFieldValue.toString());
        log.debug(sourceFieldValue.toString());
        log.debug(destinationClass.toString());
        log.debug(sourceClass.toString());
        if (sourceFieldValue == null) {
            return null;
        }
        Date dest = null;
        if (sourceFieldValue instanceof LocalDate) {
            // check to see if the object already exists
            if (existingDestinationFieldValue == null) {
                dest = new Date();
            } else {
                dest = (Date) existingDestinationFieldValue;
            }
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate sourceLocalDate = (LocalDate) sourceFieldValue;
            Date date = Date.from(sourceLocalDate.atStartOfDay(defaultZoneId).toInstant());

            log.debug(date.toString());
            return date;
        } else {
            throw new MappingException("Converter TestCustomConverter "
                    + "used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue + " and " + sourceFieldValue);
        }
    }

}
