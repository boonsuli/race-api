package com.trailerplan.model.dto;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.trailerplan.model.entity.RaceEntity;

@Data
@NoArgsConstructor
public class RaceDTO extends AbstractDTO<RaceEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    private String race_name;

    private String challenge_name;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    //private Date race_date;
    private String race_date;

    private Float distance;

    private Integer elevation_up;

    private Integer elevation_down;

    private Integer itra_point;

    private String country;

    private String city_region;

    private String departure_city;

    private String arrival_city;

    private Float maximum_time;

    private String url;

    public String toJson() throws IOException {
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(this);
    }

    public RaceEntity extractEntity() { return dtoMapper.map(this, RaceEntity.class); }

    public Class<RaceEntity> getEntityClass() { return RaceEntity.class; }
}
