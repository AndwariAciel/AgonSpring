package de.andwari.agon.business.matcher.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PointsPair {
    private Integer points;
    private Integer player;
}
