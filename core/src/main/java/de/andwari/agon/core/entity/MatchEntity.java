package de.andwari.agon.core.entity;

import static jakarta.persistence.CascadeType.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "MATCH")
public class MatchEntity extends BaseEntity {

    Long id;
    Long player1;
    Long player2;
    @Enumerated(EnumType.STRING)
    Result result;
    Long roundId;
}
