package de.andwari.agon.core.entity;

import de.andwari.agon.core.converter.IdConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "EVENT")
public class EventEntity extends BaseEntity {

    Integer currentRound;
    LocalDate date;
    String eventName;

    @Convert(converter = IdConverter.class)
    List<Long> playerIds;

    @OneToMany(cascade = MERGE, orphanRemoval = true)
    @JoinColumn(columnDefinition = "EVENT_ID")
    List<RoundEntity> rounds;
    Boolean ranked;

}
