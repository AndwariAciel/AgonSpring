package de.andwari.agon.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "MATCH")
public class MatchEntity extends BaseEntity {

    Long id;
    @ManyToOne
    PlayerEntity player1;
    @ManyToOne
    PlayerEntity player2;
    @Enumerated(EnumType.STRING)
    Result result;
    Long roundId;
}
