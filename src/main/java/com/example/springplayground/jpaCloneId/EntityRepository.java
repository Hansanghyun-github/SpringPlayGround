package com.example.springplayground.jpaCloneId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<EntityWithClone, Long> {
}
