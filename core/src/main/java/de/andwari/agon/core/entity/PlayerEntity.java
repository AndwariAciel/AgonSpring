package de.andwari.agon.core.entity;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
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
