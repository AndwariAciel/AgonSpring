package de.andwari.agon.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "PLAYER")
public class PlayerEntity extends BaseEntity {

    String name;
    String dci;
    Boolean member;

}
