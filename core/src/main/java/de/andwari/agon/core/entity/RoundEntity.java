package de.andwari.agon.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.*;

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
    @ManyToOne
    PlayerEntity bye;
    Long eventId;
}
