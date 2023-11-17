package de.andwari.agon.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "ROUND")
public class RoundEntity extends BaseEntity {

    Long id;

    @OneToMany(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(columnDefinition = "ROUND_ID")
    List<MatchEntity> matches;

    Boolean open;

    @ManyToOne(cascade = ALL)
    PlayerEntity bye;

    Long eventId;
}
