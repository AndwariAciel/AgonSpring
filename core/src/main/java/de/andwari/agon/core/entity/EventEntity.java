package de.andwari.agon.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "EVENT")
public class EventEntity extends BaseEntity {

    Integer currentRound;
    LocalDate date;
    String eventName;

    @ManyToMany(fetch = EAGER)
    List<PlayerEntity> players;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(columnDefinition = "EVENT_ID")
    List<RoundEntity> rounds;
    Boolean ranked;

}
