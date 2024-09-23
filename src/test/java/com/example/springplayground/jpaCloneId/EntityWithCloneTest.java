package com.example.springplayground.jpaCloneId;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class EntityWithCloneTest {
    @Autowired EntityRepository entityRepository;
    @Autowired EntityManager em;
    @Test
    void clone_entity_before_save() throws Exception {
        // given
        EntityWithClone entity = new EntityWithClone("name1");

        // when
        EntityWithClone clonedEntity = entity.cloneEntity();

        // then
        assertThat(clonedEntity.getId()).isNull();
    }

    @Test
    void clone_entity_after_create() throws Exception {
        // given
        EntityWithClone entity = new EntityWithClone("name1");
        entityRepository.save(entity);
        em.flush();

        // when
        EntityWithClone clonedEntity = entity.cloneEntity();

        // then
        assertThat(clonedEntity.getId()).isNotNull();
        assertThat(entityRepository.count()).isEqualTo(1);
    }

    @Test
    void save_cloned_entity() throws Exception {
        // given
        EntityWithClone entity = new EntityWithClone("name1");

        // when
        EntityWithClone clonedEntity = entity.cloneEntity();
        entityRepository.save(clonedEntity);
        em.flush();

        // then
        assertThat(clonedEntity.getId()).isNotNull();
        assertThat(entityRepository.count()).isEqualTo(1);
        System.out.println(entity.getId());
    }

    @Test
    void save_original_entity_and_cloned_entity() throws Exception {
        // given
        EntityWithClone entity = new EntityWithClone("name1");
        entityRepository.save(entity);
        em.flush();

        // when
        EntityWithClone clonedEntity = entity.cloneEntity();
        entityRepository.save(clonedEntity);
        em.flush();

        // then
        assertThat(entityRepository.count()).isEqualTo(1);
    }

}