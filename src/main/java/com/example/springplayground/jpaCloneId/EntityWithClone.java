package com.example.springplayground.jpaCloneId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class EntityWithClone {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public EntityWithClone(String name) {
        this.name = name;
    }

    public EntityWithClone cloneEntity() {
        EntityWithClone clonedEntity = new EntityWithClone(this.name);
        clonedEntity.id = this.id;
        return clonedEntity;
    }
}
