package com.trailerplan.model.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ItraPointEnum implements Serializable {

    ZERO_POINT(0, "XXS", 0, 24),
    ONE_POINT(1, "XS", 25, 44),
    TWO_POINTS(2, "S", 45, 74),
    THREE_POINTS(3, "M", 75, 114),
    FOUR_POINTS(4, "L", 115, 154),
    FIVE_POINTS(5, "XL", 155, 209),
    SIX_POINTS(6, "XXL", 210, 999);

    private ItraPointEnum(Integer itraPoint, String category, Integer kmEffortLow, Integer kmEffortHigh) {
        this.itraPoint = itraPoint;
        this.category = category;
        this.kmEffortLow = kmEffortLow;
        this.kmEffortHigh = kmEffortHigh;
    }

    private Integer itraPoint;
    public Integer getItraPoint() { return itraPoint; }
    public void setItraPoint(Integer itraPoint) { this.itraPoint = itraPoint; }

    private String category;
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    private Integer kmEffortLow;
    public Integer getKmEffortLow() { return kmEffortLow; }
    public void setKmEffortLow(Integer kmEffortLow) { this.kmEffortLow = kmEffortLow; }

    private Integer kmEffortHigh;
    public Integer getKmEffortHigh() { return kmEffortHigh; }
    public void setKmEffortHigh(Integer kmEffortHigh) { this.kmEffortHigh = kmEffortHigh; }

    public static ItraPointEnum valueOf(Integer id) {
        ItraPointEnum[] values = values();
        log.debug("id:"+id);
        ItraPointEnum ipe = Arrays.stream(values).filter(e ->
                id==e.getItraPoint()).collect(Collectors.toList()).get(0);
        return ipe!=null ? ipe : ItraPointEnum.ZERO_POINT;
    }
}
