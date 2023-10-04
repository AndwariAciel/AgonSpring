package de.andwari.agon.core.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "PLAYER")
public class PlayerEntity extends BaseEntity {

    String name;
    String dci;
    Boolean member;

}
