package com.trailerplan.model.entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import javax.persistence.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import com.trailerplan.model.dto.RaceDTO;

@Entity
@org.springframework.data.relational.core.mapping.Table("RaceEntity")
@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RaceEntity extends AbstractEntity<RaceEntity, RaceDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "ID", unique=true, nullable=false, insertable=false, updatable=false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "race_name")
    private String race_name;

    @Column(name = "challenge_name")
    private String challenge_name;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "race_date")
    //private LocalDate race_date;
    private String race_date;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "elevation_up")
    private Integer elevation_up;

    @Column(name = "elevation_down")
    private Integer elevation_down;

    @Column(name = "itra_point")
    private Integer itra_point;

    @Column(name = "country")
    private String country;

    @Column(name = "city_region")
    private String city_region;

    @Column(name = "departure_city")
    private String departure_city;

    @Column(name = "arrival_city")
    private String arrival_city;

    @Column(name = "maximum_time")
    private Float maximum_time;

    @Column(name = "url")
    private String url;

    public RaceDTO extractDTO() {
        return entityMapper.map(this, RaceDTO.class);
    }

    public RaceEntity setPropertiesFromEntity(final RaceEntity raceEntity){
        RaceEntity newRaceEntity = new RaceEntity();
        try {
            BeanUtils.copyProperties(newRaceEntity, raceEntity);
        } catch(InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage());
        } finally {
            return newRaceEntity;
        }
    }

    public RaceEntity setPropertiesFromDTO(final RaceDTO raceDTO) {
        try {
            BeanUtils.copyProperties(this, raceDTO);
        } catch(InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage());
        } finally {
            return this;
        }
    }
}


